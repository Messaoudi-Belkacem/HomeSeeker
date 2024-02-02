package com.example.darckoum.data.model

import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State

data class Announcement(
    val id: Int,
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val propertyType: PropertyType,
    val location: String,
    val state: State,
    val price: Int,
    val description: String,
)