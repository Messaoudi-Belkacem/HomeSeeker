package com.example.darckoum.data.repository

import com.example.darckoum.data.model.House
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.TransactionType

class HouseRepository {
    private val houseList = mutableListOf(
        House(
            id = 0,
            title = "house 1",
            location = "Batna",
            propertyType = PropertyType.VILLA,
            transactionType = TransactionType.FOR_SALE,
            price = 15000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        ),
        House(
            id = 1,
            title = "house 2",
            location = "Batna",
            propertyType = PropertyType.APARTMENT,
            transactionType = TransactionType.FOR_SALE,
            price = 5000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        ),
        House(
            id = 2,
            title = "house 3",
            location = "Setif",
            propertyType = PropertyType.APARTMENT,
            transactionType = TransactionType.FOR_SALE,
            price = 75000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        ),
        House(
            id = 3,
            title = "house 4",
            location = "Setif",
            propertyType = PropertyType.APARTMENT,
            transactionType = TransactionType.FOR_SALE,
            price = 9000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        ),
        House(
            id = 4,
            title = "house 5",
            location = "Algeirs",
            propertyType = PropertyType.VILLA,
            transactionType = TransactionType.FOR_SALE,
            price = 25000000,
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        )
    )

    fun getAllData(): List<House> {
        return houseList.toList()
    }

    fun getAllNewData(): List<House> {
        return houseList.drop(5).toList()
    }

    fun addHouse(newHouse: House) {

        val newId = houseList.size
        val houseWithNewId = newHouse.copy(id = newId)

        houseList.add(houseWithNewId)
    }
}