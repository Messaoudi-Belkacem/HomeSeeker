package com.example.darckoum.data.model.request

import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State

data class AnnouncementRequest(
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val propertyType: String,
    val location: String,
    val state: String,
    val price: Double,
    val description: String,
    val owner: String
)

data class AnnouncementResponse(
    val id: Int,
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val propertyType: String,
    val location: String,
    val state: String,
    val price: Double,
    val description: String,
    val owner: String
)