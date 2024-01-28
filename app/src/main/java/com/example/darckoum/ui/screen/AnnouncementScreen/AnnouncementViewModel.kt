package com.example.darckoum.ui.screen.AnnouncementScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AnnouncementViewModel : ViewModel() {

    private val _counter = mutableStateOf(0)
    val counter: MutableState<Int> = _counter

    fun incrementCounter() {
        _counter.value += 1
    }
}