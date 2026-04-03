package com.turingheights.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingheights.core.navigation.AppScreens
import com.turingheights.core.navigation.Navigator
import com.turingheights.domain.models.Resource
import com.turingheights.domain.usecases.GetCharactersUseCase
import com.turingheights.domain.usecases.GetFavouritesUseCase
import com.turingheights.domain.usecases.UpdateCharacterUseCase
import com.turingheights.feeds.action.HomeScreenAction
import com.turingheights.feeds.state.HomeScreenState
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
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState(
        selected = savedStateHandle.get<List<Int>>(ARG_SELECTED) ?: emptyList(),
        page = savedStateHandle.get<Int>(ARG_PAGE) ?: 1,
        error = savedStateHandle.get<String>(ARG_ERROR),
        isRefreshing = savedStateHandle.get<Boolean>(ARG_REFRESHING) ?: false,
        isLoading = savedStateHandle.get<Boolean>(ARG_LOADING) ?: false,
        isLoadingMore = savedStateHandle.get<Boolean>(ARG_LOADING_MORE) ?: false,
    ))
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
        _state.update {
            it.copy(
                isRefreshing = true,
                page = 1
            )
        }
        load()
    }

    private fun load() = viewModelScope.launch {
        getCharactersUseCase(state.value.page).collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = if (it.page != 1) null else res.message,
                            isLoading = false,
                            isLoadingMore = false,
                            isRefreshing = false,
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = if (it.isRefreshing) false else it.page == 1,
                            isRefreshing = false,
                            isLoadingMore = it.page != 1
                        )
                    }
                }
                is Resource.Success -> {
                    val newCharacters = res.data

                    _state.update {
                        it.copy(
                            characters = if (it.page == 1) listOf(*newCharacters.toTypedArray()) else it.characters.plus(newCharacters),
                            isLoading = false,
                            isLoadingMore = if (it.page != 1) false else newCharacters.isEmpty(),
                            isRefreshing = false,
                            page = it.page + 1,
                            error = null,
                        )
                    }
                }
            }
        }
    }

    private fun startSelection(action: HomeScreenAction.StartSelection) {
        _state.update {
            it.copy(
                selected = listOf(action.id)
            )
        }
        updateSavedState()
    }

    private fun addSelection(action: HomeScreenAction.AddToSelection) {
        _state.update {
            it.copy(
                selected = it.selected.plus(action.id)
            )
        }
        updateSavedState()
    }

    private fun removeSelection(action: HomeScreenAction.RemoveFromSelection) {
        _state.update {
            it.copy(
                selected = it.selected.minus(action.id)
            )
        }
        updateSavedState()
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

    private fun updateSavedState(
        selected: List<Int> = state.value.selected,
        page: Int = state.value.page,
        error: String? = state.value.error,
        refreshing: Boolean = state.value.isRefreshing,
        loading: Boolean = state.value.isLoading,
        loadingMore: Boolean = state.value.isLoadingMore,
    ) {
        savedStateHandle[ARG_SELECTED] = selected
        savedStateHandle[ARG_PAGE] = page
        savedStateHandle[ARG_ERROR] = error
        savedStateHandle[ARG_REFRESHING] = refreshing
        savedStateHandle[ARG_LOADING] = loading
        savedStateHandle[ARG_LOADING_MORE] = loadingMore
    }

}

const val ARG_SELECTED = "ARG_SELECTED"
const val ARG_PAGE = "ARG_PAGE"
const val ARG_ERROR = "ARG_ERROR"
const val ARG_REFRESHING = "ARG_REFRESHING"
const val ARG_LOADING = "ARG_LOADING"
const val ARG_LOADING_MORE = "ARG_LOADING_MORE"