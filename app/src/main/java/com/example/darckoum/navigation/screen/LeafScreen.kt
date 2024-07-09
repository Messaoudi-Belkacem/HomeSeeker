package com.example.darckoum.navigation.screen

sealed class LeafScreen(val route: String) {
    object Main: LeafScreen(route = "main_screen")
    object Announcement : LeafScreen(route = "announcement")
    object OwnedAnnouncement : LeafScreen(route = "owned_announcement")
}