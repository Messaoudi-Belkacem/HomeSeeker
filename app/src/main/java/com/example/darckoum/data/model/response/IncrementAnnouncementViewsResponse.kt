package com.example.darckoum.data.model.response

import com.example.darckoum.data.model.Announcement
import com.example.darckoum.data.model.User

data class IncrementAnnouncementViewsResponse(val announcement: Announcement, val message: String)
