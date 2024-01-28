package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.darckoum.ui.screen.MainScreen
import com.example.darckoum.ui.screen.WelcomeScreen1
import com.example.darckoum.ui.screen.WelcomeScreen2

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val time = 1000
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome1.route,
        enterTransition = { fadeIn(animationSpec = tween(time)) },
        exitTransition = { fadeOut(animationSpec = tween(time)) },
        popEnterTransition = { fadeIn(animationSpec = tween(time)) },
        popExitTransition = { fadeOut(animationSpec = tween(time)) }
    ) {
        composable(
            route = Screen.Welcome1.route,

        ) {
            WelcomeScreen1(navController = navController)
        }
        composable(
            route = Screen.Welcome2.route
        ) {
            WelcomeScreen2(navController = navController)
        }
        composable(
            route = Screen.Main.route
        ) {
            MainScreen()
        }
    }
}