package com.turingheights.favourites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingheights.domain.usecases.GetCharactersUseCase
import com.turingheights.domain.usecases.GetFavouritesUseCase
import com.turingheights.domain.usecases.UpdateCharacterUseCase
import com.turingheights.feeds.action.HomeScreenAction
import com.turingheights.feeds.state.HomeScreenState
import com.turingheights.navigation.AppScreens
import com.turingheights.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val navigator: Navigator,
): ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getFavouritesUseCase().collectLatest { faves ->
                _state.update {
                    it.copy(
                        favourites = faves.map { it.id }
                    )
                }
            }
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.Favourite -> favourite(action)
            HomeScreenAction.Load -> load()
            HomeScreenAction.NavigateToFavourite -> navigator.navigate(
                appScreens = AppScreens.FAVOURITES
            )
            HomeScreenAction.Refresh -> refresh()
            is HomeScreenAction.StartSelection -> startSelection(action)
            is HomeScreenAction.UnFavourite -> unFavourite(action)
            is HomeScreenAction.AddToSelection -> addSelection(action)
            is HomeScreenAction.RemoveFromSelection -> removeSelection(action)
        }
    }

    private fun refresh() = viewModelScope.launch {

    }

    private fun load() = viewModelScope.launch {
        getCharactersUseCase(state.value.page).collectLatest { newCharacters ->
            _state.update {
                it.copy(
                    characters = it.characters.plus(newCharacters),
                    isLoading = false,
                    isRefreshing = false,
                )
            }
        }
    }

    private fun startSelection(action: HomeScreenAction.StartSelection) {
        _state.update {
            it.copy(
                selected = listOf(action.id)
            )
        }
    }

    private fun addSelection(action: HomeScreenAction.AddToSelection) {
        _state.update {
            it.copy(
                selected = it.selected.plus(action.id)
            )
        }
    }

    private fun removeSelection(action: HomeScreenAction.RemoveFromSelection) {
        _state.update {
            it.copy(
                selected = it.selected.minus(action.id)
            )
        }
    }

    private fun unFavourite(action: HomeScreenAction.UnFavourite) = viewModelScope.launch {
        val character = _state.value.characters.find { it.id == action.id }
        character?.let {
            updateCharacterUseCase(it.copy(favourite = false))
        }
    }

    private fun favourite(action: HomeScreenAction.Favourite) = viewModelScope.launch {
        val characters = _state.value.characters.filter{ action.ids.contains(it.id) }
        println("Characters: $characters")
        characters.forEach {
            updateCharacterUseCase(it.copy(favourite = true))
        }
        _state.update {
            it.copy(
                selected = emptyList()
            )
        }
    }

}