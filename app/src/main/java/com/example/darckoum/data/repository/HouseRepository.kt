package com.example.darckoum.data.repository

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State

class HouseRepository {
    private val houseList = mutableListOf(
        Announcement(
            id = 0,
            title = "Modern Villa with Stunning Views",
            location = "Rue Ben Harkat Mohamed, Setif",
            propertyType = PropertyType.VILLA,
            price = 15000000,
            description = "This modern villa offers breathtaking views and is located in a prime area of Batna. It features 4 bedrooms, a spacious living area, and a beautiful garden.",
            area = 200,
            numberOfRooms = 4,
            state = State.Setif
        ),
        Announcement(
            id = 1,
            title = "Luxurious Apartment in the Heart of the City",
            location = "5CV8+6FJ, Sétif 19000",
            propertyType = PropertyType.APARTMENT,
            price = 5000000,
            description = "Experience luxury living in this centrally located apartment. With 4 bedrooms, a stylish kitchen, and proximity to key amenities, it's an ideal choice for urban living.",
            area = 150,
            numberOfRooms = 4,
            state = State.Setif
        ),
        Announcement(
            id = 2,
            title = "Charming Family Home with Garden",
            location = "5 Rue Laffi, Setif 19000",
            propertyType = PropertyType.CONDO,
            price = 8000000,
            description = "This charming house is perfect for a family. It features a spacious garden, 3 bedrooms, and a cozy living space. Enjoy a peaceful neighborhood and convenient access to schools.",
            area = 180,
            numberOfRooms = 3,
            state = State.Setif
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