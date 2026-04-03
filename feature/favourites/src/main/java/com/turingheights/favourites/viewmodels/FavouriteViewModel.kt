package com.turingheights.favourites.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingheights.domain.usecases.GetFavouritesUseCase
import com.turingheights.domain.usecases.UpdateCharacterUseCase
import com.turingheights.favourites.action.FavouriteScreenAction
import com.turingheights.favourites.state.FavouriteScreenState
import com.turingheights.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val navigator: Navigator,
): ViewModel() {

    private val _state = MutableStateFlow(FavouriteScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getFavouritesUseCase().collectLatest {
                _state.value = _state.value.copy(
                    favourites = it
                )
            }
        }
    }

    fun onAction(action: FavouriteScreenAction) {
        when (action) {
            FavouriteScreenAction.NavigateBack -> {
                navigator.navigateBack()
            }
            is FavouriteScreenAction.UnFavourite -> {
                unFavourite(action)
            }
        }
    }

    private fun unFavourite(action: FavouriteScreenAction.UnFavourite) = viewModelScope.launch {
        val character = _state.value.favourites.find { it.id == action.id }
        character?.let {
            updateCharacterUseCase(it.copy(favourite = false))
        }
    }

}