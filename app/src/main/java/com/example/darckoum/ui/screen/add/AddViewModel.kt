package com.example.darckoum.ui.screen.add

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {

    private val _counter = mutableIntStateOf(0)
    val counter: MutableState<Int> = _counter

    fun incrementCounter() {
        _counter.intValue += 1
    }
}