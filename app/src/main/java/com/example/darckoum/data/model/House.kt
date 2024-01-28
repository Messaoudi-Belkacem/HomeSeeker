package com.example.darckoum.data.model

import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.TransactionType

data class House(
    val id: Int,
    val title: String,
    val location: String,
    val propertyType: PropertyType,
    val transactionType: TransactionType,
    val price: Int,
    // val photos: List<Int>,
    val description: String
)