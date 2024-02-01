package com.example.darckoum.data.repository

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.enum_classes.PropertyType

class HouseRepository {
    private val houseList = mutableListOf(
        Announcement(
            id = 0,
            title = "house 1",
            location = "Batna",
            propertyType = PropertyType.VILLA,
            price = 15000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            area = 150,
            numberOfRooms = 4
        ),
        Announcement(
            id = 1,
            title = "house 2",
            location = "Batna",
            propertyType = PropertyType.APARTMENT,
            price = 5000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            area = 150,
            numberOfRooms = 4
        )
    )

    fun getAllData(): List<Announcement> {
        return houseList.toList()
    }

    fun getAllNewData(): List<Announcement> {
        return houseList.drop(5).toList()
    }

    fun addHouse(newHouse: Announcement) {

        val newId = houseList.size
        val houseWithNewId = newHouse.copy(id = newId)

        houseList.add(houseWithNewId)
    }
}