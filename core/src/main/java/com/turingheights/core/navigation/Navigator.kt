package com.turingheights.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class Navigator {

    private val _navigationActions = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)
    val navigationActions: SharedFlow<NavigationAction> = _navigationActions

    fun navigateBack() {
        _navigationActions.tryEmit(NavigationAction.NavigateBack)
    }

    fun navigateToScreen(
        route: Any
    ) {
        _navigationActions.tryEmit(NavigationAction.NavigateTo(route = route))
    }

    fun navigate(appScreens: AppScreens) {
        _navigationActions.tryEmit(NavigationAction.Navigate(appScreens = appScreens))
    }

}