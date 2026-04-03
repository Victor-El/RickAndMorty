package com.turingheights.favourites.action

sealed interface FavouriteScreenAction {
    data object NavigateBack: FavouriteScreenAction
    data class UnFavourite(val id: Int): FavouriteScreenAction
}