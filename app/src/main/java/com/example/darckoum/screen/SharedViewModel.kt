package com.example.darckoum.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.request.AnnouncementResponse

class SharedViewModel : ViewModel() {
    var announcement by mutableStateOf<Announcement?>(null)
    var ownedAnnouncement by mutableStateOf<Announcement?>(null)
}