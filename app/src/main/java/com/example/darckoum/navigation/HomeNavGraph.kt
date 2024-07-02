package com.example.darckoum.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.navigation.screen.LeafScreen
import com.example.darckoum.ui.screen.add.AddScreen
import com.example.darckoum.ui.screen.announcement.AnnouncementScreen
import com.example.darckoum.ui.screen.home.HomeScreen
import com.example.darckoum.ui.screen.profile.ProfileScreen

@Composable
fun HomeNavGraph(bottomBarNavHostController: NavHostController) {
    NavHost(
        navController = bottomBarNavHostController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(bottomBarNavHostController = bottomBarNavHostController)
        }
        composable(route = BottomBarScreen.Add.route) {
            AddScreen(bottomBarNavHostController = bottomBarNavHostController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(bottomBarNavHostController = bottomBarNavHostController)
        }
        navigation(
            route = Graph.DETAILS,
            startDestination = LeafScreen.Announcement.route
        ) {
            composable(route = LeafScreen.Announcement.route) {
                AnnouncementScreen(bottomBarNavHostController = bottomBarNavHostController)
            }
        }
    }
}