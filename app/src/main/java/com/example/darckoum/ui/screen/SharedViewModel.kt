package com.example.darckoum.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.request.AnnouncementResponse

class SharedViewModel : ViewModel() {
    var announcementWithImages by mutableStateOf<AnnouncementResponse?>(null)

    fun addAnnouncementResponse(announcementResponse: AnnouncementResponse) {
        announcementWithImages = announcementResponse
    }

}