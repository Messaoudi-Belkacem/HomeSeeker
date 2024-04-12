package com.example.darckoum

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.darckoum.data.repository.Repository

class MainViewModel(private val repository: Repository, application: Application) : ViewModel() {

}