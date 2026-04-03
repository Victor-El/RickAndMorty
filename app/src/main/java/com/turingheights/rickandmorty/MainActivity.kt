package com.turingheights.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.turingheights.core.ui.theme.RickAndMortyTheme
import com.turingheights.core.ui.theme.darkBackgroundBlue
import com.turingheights.favourites.screen.FavouriteScreenRoute
import com.turingheights.feeds.screen.HomeScreenRoute
import com.turingheights.core.navigation.AppScreens
import com.turingheights.core.navigation.NavigationAction
import com.turingheights.core.navigation.Navigator
import com.turingheights.rickandmorty.navigation.MainNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    navigator.navigationActions.collect {
                        when (it) {
                            NavigationAction.NavigateBack -> navController.popBackStack()
                            is NavigationAction.NavigateTo -> navController.navigate(it.route)
                            is NavigationAction.Navigate -> when (it.appScreens) {
                                AppScreens.HOME -> navController.navigate(HomeScreenRoute)
                                AppScreens.FAVOURITES -> navController.navigate(FavouriteScreenRoute)
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = darkBackgroundBlue,
                ) { innerPadding ->
                    MainNavGraph(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        homeRoute = HomeScreenRoute,
                        navController = navController
                    )
                }
            }
        }
    }
}