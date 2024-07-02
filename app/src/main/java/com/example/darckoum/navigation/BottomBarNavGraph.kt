package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.ui.screen.SearchScreen
import com.example.darckoum.ui.screen.SharedViewModel
import com.example.darckoum.ui.screen.add.AddScreen
import com.example.darckoum.ui.screen.add.AddViewModel
import com.example.darckoum.ui.screen.announcement.AnnouncementScreen
import com.example.darckoum.ui.screen.announcement.AnnouncementViewModel
import com.example.darckoum.ui.screen.home.HomeScreen
import com.example.darckoum.ui.screen.profile.ProfileScreen
import com.example.darckoum.ui.screen.profile.ProfileViewModel

@Composable
fun BottomNavGraph(bottomBarNavController: NavHostController, navController: NavHostController, addViewModel: AddViewModel, profileViewModel: ProfileViewModel, announcementViewModel: AnnouncementViewModel, sharedViewModel: SharedViewModel) {

    val time = 500
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomBarScreen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(time)) },
        exitTransition = { fadeOut(animationSpec = tween(time)) },
        popEnterTransition = { fadeIn(animationSpec = tween(time)) },
        popExitTransition = { fadeOut(animationSpec = tween(time)) }
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(bottomBarNavController)
        }

        composable(route = BottomBarScreen.Add.route) {
            AddScreen(bottomBarNavController, addViewModel)
        }

        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(navController, profileViewModel)
        }

        composable(
            route = BottomBarScreen.Announcement.route
        ) {
            AnnouncementScreen(announcementViewModel = announcementViewModel, sharedViewModel = sharedViewModel, bottomBarNavHostController = bottomBarNavController)
        }

        composable(
            route = BottomBarScreen.Search.route
        ) {
            SearchScreen(bottomBarNavController)
        }
    }
}