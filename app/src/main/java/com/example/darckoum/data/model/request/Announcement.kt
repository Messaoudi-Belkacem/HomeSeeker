package com.example.darckoum.data.model.request

import android.net.Uri

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
    // val selectedImageUris: List<Uri>
)

data class AnnouncementResponse(
    var id: Long? = null,
    var title: String? = null,
    var area: Int = 0,
    var numberOfRooms: Int = 0,
    var propertyType: String? = null,
    var location: String? = null,
    var state: String? = null,
    var price: Double = 0.0,
    var description: String? = null,
    var owner: String? = null,
    var images: List<Any>? = null
)