package com.example.darckoum.navigation.screen

sealed class AuthenticationScreen(val route: String) {
    object LogIn: AuthenticationScreen(route = "log_in")
    object SignUp: AuthenticationScreen(route = "sign_up")
}