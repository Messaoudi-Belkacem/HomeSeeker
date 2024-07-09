package com.example.darckoum.data.state

sealed class OwnedAnnouncementState {
    object Initial : OwnedAnnouncementState()
    object Loading : OwnedAnnouncementState()
    object Success : OwnedAnnouncementState()
    data class Error(val message: String) : OwnedAnnouncementState()
}