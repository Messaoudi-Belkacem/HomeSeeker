package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.darckoum.MainViewModel
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.navigation.screen.LeafScreen
import com.example.darckoum.screen.search.SearchScreen
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.screen.add.AddScreen
import com.example.darckoum.screen.announcement.AnnouncementScreen
import com.example.darckoum.screen.announcement.owned.OwnedAnnouncementScreen
import com.example.darckoum.screen.home.HomeScreen
import com.example.darckoum.screen.profile.ProfileScreen

@Composable
fun HomeNavGraph(
    bottomBarNavHostController: NavHostController,
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    val time = 500
    NavHost(
        navController = bottomBarNavHostController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(time)) },
        exitTransition = { fadeOut(animationSpec = tween(time)) },
        popEnterTransition = { fadeIn(animationSpec = tween(time)) },
        popExitTransition = { fadeOut(animationSpec = tween(time)) }
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                bottomBarNavHostController = bottomBarNavHostController,
                mainViewModel = mainViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = BottomBarScreen.Add.route) {
            AddScreen(
                bottomBarNavHostController = bottomBarNavHostController,
                sharedViewModel = sharedViewModel,
                paddingValues = paddingValues
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(
                bottomBarNavHostController = bottomBarNavHostController,
                navHostController = navHostController,
                mainViewModel = mainViewModel,
                sharedViewModel = sharedViewModel,
                paddingValues = paddingValues
            )
        }
        navigation(
            route = Graph.DETAILS,
            startDestination = LeafScreen.Announcement.route
        ) {
            composable(route = LeafScreen.Announcement.route) {
                AnnouncementScreen(
                    bottomBarNavHostController = bottomBarNavHostController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
        navigation(
            route = Graph.MY_DETAILS,
            startDestination = LeafScreen.OwnedAnnouncement.route
        ) {
            composable(route = LeafScreen.OwnedAnnouncement.route) {
                OwnedAnnouncementScreen(
                    bottomBarNavHostController = bottomBarNavHostController,
                    sharedViewModel = sharedViewModel
                )
            }
        }

        navigation(
            route = Graph.SEARCH,
            startDestination = LeafScreen.Search.route
        ) {
            composable(route = LeafScreen.Search.route) {
                SearchScreen(
                    bottomBarNavHostController = bottomBarNavHostController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}