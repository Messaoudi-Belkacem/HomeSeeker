package com.example.darckoum.data.model

import com.example.darckoum.ui.screen.profile.Gender

data class Profile(
    val userId: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val dateOfBirth: String,
    val phoneNumber: String,
    val accountCreationDate: String,
    val password: String,
    val housesAnnounced: List<Announcement>
)
