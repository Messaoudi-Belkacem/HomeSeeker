package com.example.darckoum.navigation

import com.example.darckoum.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int
) {
    object Home : BottomBarScreen(
        route = "home",
        icon = R.drawable.ic_home
    )

    object Announcement : BottomBarScreen(
        route = "Announcement",
        icon = R.drawable.ic_home
    )

    object Search : BottomBarScreen(
        route = "Search",
        icon = R.drawable.ic_home
    )

    object Add : BottomBarScreen(
        route = "add",
        icon = R.drawable.ic_add
    )

    object Profile : BottomBarScreen(
        route = "profile",
        icon = R.drawable.ic_profile
    )
}