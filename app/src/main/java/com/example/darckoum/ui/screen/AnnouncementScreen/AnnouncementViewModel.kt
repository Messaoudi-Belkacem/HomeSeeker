package com.example.darckoum.ui.screen.AnnouncementScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.items.formatPrice

class AnnouncementViewModel(houseRepository: HouseRepository) : ViewModel() {

    private val announcement = houseRepository.getAllData()[0]

    private val _title = mutableStateOf(announcement.title)
    val title: MutableState<String> = _title

    private val _area = mutableIntStateOf(announcement.area)
    val area: MutableState<Int> = _area

    private val _numberOfRooms = mutableIntStateOf(announcement.numberOfRooms)
    val numberOfRooms: MutableState<Int> = _numberOfRooms

    private val _location = mutableStateOf(announcement.location)
    val location: MutableState<String> = _location

    private val _propertyType = mutableStateOf(announcement.propertyType)
    val propertyType: MutableState<PropertyType> = _propertyType

    private val _price = mutableIntStateOf(announcement.price)
    val price: MutableState<Int> = _price

    private val _description = mutableStateOf(announcement.description)
    val description: MutableState<String> = _description

    fun getFormattedPrice(): String {
        return formatPrice(_price.intValue)
    }

}