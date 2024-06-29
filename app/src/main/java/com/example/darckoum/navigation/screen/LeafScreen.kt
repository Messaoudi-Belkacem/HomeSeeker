package com.example.darckoum.navigation.screen

sealed class LeafScreen(val route: String) {
    object Welcome: LeafScreen(route = "welcome_screen")
    object Main: LeafScreen(route = "main_screen")
    object Announcement : LeafScreen(route = "announcement")
}