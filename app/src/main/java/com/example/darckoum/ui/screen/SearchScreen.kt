package com.example.darckoum.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.items.CustomItem
import com.example.darckoum.items.CustomSearchItem
import com.example.darckoum.ui.screen.HomeScreen.SearchBarD
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C3

@Composable
fun SearchScreen(houseRepository: HouseRepository, navController: NavController) {

    val getAllData = houseRepository.getAllData()
    val getAllNewData = houseRepository.getAllNewData()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = C2)
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchItem(houseRepository.getAllData()[2], navController = navController)
        CustomSearchItem(houseRepository.getAllData()[3], navController = navController)
        CustomSearchItem(houseRepository.getAllData()[4], navController = navController)
    }
}