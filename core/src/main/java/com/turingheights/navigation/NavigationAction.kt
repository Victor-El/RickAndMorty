package com.turingheights.navigation

sealed interface NavigationAction {
    data class NavigateTo(val route: Any) : NavigationAction
    data class Navigate(val appScreens: AppScreens): NavigationAction
    data object NavigateBack : NavigationAction
}
