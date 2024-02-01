package com.example.darckoum.data.model

import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.TransactionType

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