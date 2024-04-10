package com.example.darckoum.ui.screen.announcement

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.items.formatPrice

class AnnouncementViewModel(repository: Repository) : ViewModel() {

    /*private val _announcementResponse = mutableStateOf<AnnouncementResponse?>(null)


    private val _title = mutableStateOf(_announcementResponse.value?.title)
    val title: MutableState<String?> = _title

    private val _area = mutableIntStateOf(_announcementResponse.value?.area)
    val area: MutableState<Int> = _area

    private val _numberOfRooms = mutableIntStateOf(announcementResponse.numberOfRooms)
    val numberOfRooms: MutableState<Int> = _numberOfRooms

    private val _location = mutableStateOf(announcementResponse.location)
    val location: MutableState<String?> = _location

    private val _state = mutableStateOf(announcementResponse.state)
    val state: MutableState<String?> = _state

    private val _propertyType = mutableStateOf(announcementResponse.propertyType)
    val propertyType: MutableState<String?> = _propertyType

    private val _price = mutableDoubleStateOf(announcementResponse.price)
    val price: MutableState<Double> = _price

    private val _description = mutableStateOf(announcementResponse.description)
    val description: MutableState<String?> = _description

    private val _images = mutableStateOf(announcementResponse.images)
    val images: MutableState<List<ByteArray>?> = _images*/

    fun getFormattedPrice(price : Double): String {
        return formatPrice(price.toInt())
    }

}