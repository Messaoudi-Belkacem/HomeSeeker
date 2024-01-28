package com.example.darckoum.navigation

import com.example.darckoum.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_home
    )

    object Announcement : BottomBarScreen(
        route = "Announcement",
        title = "announcement",
        icon = R.drawable.ic_home
    )

    object Search : BottomBarScreen(
        route = "Search",
        title = "search",
        icon = R.drawable.ic_home
    )

    object Add : BottomBarScreen(
        route = "add",
        title = "Add",
        icon = R.drawable.ic_add
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.ic_profile
    )
}