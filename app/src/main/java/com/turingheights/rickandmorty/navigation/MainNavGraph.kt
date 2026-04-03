package com.turingheights.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.turingheights.feeds.screen.HomeScreen
import com.turingheights.feeds.screen.HomeScreenRoute
import com.turingheights.favourites.screen.FavouriteScreen
import com.turingheights.favourites.screen.FavouriteScreenRoute

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeRoute: Any,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = homeRoute,
    ) {
        composable<HomeScreenRoute> {
            HomeScreen()
        }

        composable<FavouriteScreenRoute> {
            FavouriteScreen()
        }
    }
}