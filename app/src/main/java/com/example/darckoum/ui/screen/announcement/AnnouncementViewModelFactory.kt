package com.example.darckoum.ui.screen.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.data.repository.Repository

class AnnouncementViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnouncementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnnouncementViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}