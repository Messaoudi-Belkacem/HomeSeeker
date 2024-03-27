package com.example.darckoum.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen(route = "welcome_screen")
    object LogIn: Screen(route = "log_in")
    object SignUp: Screen(route = "sign_up")
    object Main: Screen(route = "main_screen")
}