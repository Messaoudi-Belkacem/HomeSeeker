package com.example.darckoum.screen.announcement

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.screen.common.formatPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    /*private val _announcementResponse = mutableStateOf<AnnouncementResponse?>(null)
    val announcementResponse: MutableState<AnnouncementResponse?> = _announcementResponse

    private val _title = mutableStateOf(_announcementResponse.value?.title)
    val title: MutableState<String?> = _title

    private val _area = mutableIntStateOf(_announcementResponse.value?.area)
    val area: MutableState<Int> = _area

    private val _numberOfRooms = mutableIntStateOf(announcementResponse.value.numberOfRooms)
    val numberOfRooms: MutableState<Int> = _numberOfRooms

    private val _location = mutableStateOf(announcementResponse.value.location)
    val location: MutableState<String?> = _location

    private val _state = mutableStateOf(announcementResponse.value.state)
    val state: MutableState<String?> = _state

    private val _propertyType = mutableStateOf(announcementResponse.value.propertyType)
    val propertyType: MutableState<String?> = _propertyType

    private val _price = mutableDoubleStateOf(announcementResponse.value.price)
    val price: MutableState<Double> = _price

    private val _description = mutableStateOf(announcementResponse.value.description)
    val description: MutableState<String?> = _description

    private val _images = mutableStateOf(announcementResponse.value.images)
    val images: MutableState<List<ByteArray>?> = _images*/

    fun getFormattedPrice(price : Double): String {
        return formatPrice(price)
    }

}