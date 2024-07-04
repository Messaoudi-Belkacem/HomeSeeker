package com.example.darckoum.data.model.request

import com.example.darckoum.data.model.Announcement

data class AddAnnouncementRequest(
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val propertyType: String,
    val location: String,
    val state: String,
    val price: Double,
    val description: String,
    val owner: String,
)

data class AnnouncementResponse(
    val content: List<Announcement>
)