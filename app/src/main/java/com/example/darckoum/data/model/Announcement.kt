package com.example.darckoum.data.model

import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State

data class Announcement(
    val id: Int,
    val title: String,
    val area: Int,
    val numberOfRooms: Int,
    val propertyType: String,
    val location: String,
    val state: String,
    val price: Double,
    val description: String,
    val owner: String,
    val user: String,
    val imageNames: List<String>
)