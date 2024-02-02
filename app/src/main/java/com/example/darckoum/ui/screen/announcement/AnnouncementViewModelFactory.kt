package com.example.darckoum.ui.screen.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darckoum.data.repository.HouseRepository
class AnnouncementViewModelFactory(private val houseRepository: HouseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnouncementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnnouncementViewModel(houseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}