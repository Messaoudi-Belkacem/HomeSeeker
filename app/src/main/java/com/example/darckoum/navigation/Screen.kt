package com.example.darckoum.navigation

sealed class Screen(val route: String) {
    object Welcome1: Screen(route = "welcome_screen_1")
    object Welcome2: Screen(route = "welcome_screen_2")
    object Main: Screen(route = "main_screen")
}