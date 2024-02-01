package com.example.darckoum.data.model

import com.example.darckoum.data.model.enum_classes.PropertyType

data class Announcement(
    val id: Int,
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val location: String,
    val propertyType: PropertyType,
    val price: Int,
    val description: String,
)