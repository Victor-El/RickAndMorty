package com.turingheights.feeds.action

sealed interface HomeScreenAction {
    data object Refresh: HomeScreenAction
    data object Load: HomeScreenAction
    data class Favourite(val ids: List<Int>): HomeScreenAction
    data class UnFavourite(val id: Int): HomeScreenAction
    data class StartSelection(val id: Int): HomeScreenAction
    data class AddToSelection(val id: Int): HomeScreenAction
    data class RemoveFromSelection(val id: Int): HomeScreenAction
    data object NavigateToFavourite: HomeScreenAction
}