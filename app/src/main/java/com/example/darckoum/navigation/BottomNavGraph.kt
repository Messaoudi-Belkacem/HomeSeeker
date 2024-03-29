package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.ui.screen.add.AddScreen
import com.example.darckoum.ui.screen.announcement.AnnouncementScreen
import com.example.darckoum.ui.screen.home.HomeScreen
import com.example.darckoum.ui.screen.profile.ProfileScreen
import com.example.darckoum.ui.screen.SearchScreen
import com.example.darckoum.ui.screen.add.AddViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, addViewModel: AddViewModel) {

    val houseRepository = HouseRepository()

    val time = 100
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(time)) },
        exitTransition = { fadeOut(animationSpec = tween(time)) },
        popEnterTransition = { fadeIn(animationSpec = tween(time)) },
        popExitTransition = { fadeOut(animationSpec = tween(time)) }
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(houseRepository, navController)
        }

        composable(route = BottomBarScreen.Add.route) {
            AddScreen(navController, addViewModel)
        }

        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }

        composable(
            route = BottomBarScreen.Announcement.route
        ) {
            AnnouncementScreen(houseRepository)
        }

        composable(
            route = BottomBarScreen.Search.route
        ) {
            SearchScreen(houseRepository, navController)
        }
    }
}