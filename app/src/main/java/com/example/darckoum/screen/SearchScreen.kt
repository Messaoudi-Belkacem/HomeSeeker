package com.example.darckoum.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



@Composable
fun SearchScreen( navController: NavController) {




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {/*
        CustomSearchItem(houseRepository.getAllData()[2], navController = navController)
        CustomSearchItem(houseRepository.getAllData()[3], navController = navController)
        CustomSearchItem(houseRepository.getAllData()[4], navController = navController)*/
    }
}