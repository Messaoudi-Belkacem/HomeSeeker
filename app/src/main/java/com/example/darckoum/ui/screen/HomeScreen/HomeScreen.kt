package com.example.darckoum.ui.screen.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.darckoum.R
import com.example.darckoum.items.CustomItem
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.navigation.BottomBarScreen
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C3
import com.example.darckoum.ui.theme.C5

@Composable
fun HomeScreen(houseRepository: HouseRepository, navController: NavController) {

    val getAllData = houseRepository.getAllData()
    val getAllNewData = houseRepository.getAllNewData()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                SearchBarD(navController)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = C2)
                    .padding(bottom = 75.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = C2)
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recommended for you",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = C3,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(
                            text = "See more",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = C3,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = getAllData) { house ->
                            CustomItem(house = house, navController)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = C2)
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My posts",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = C3,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(
                            text = "See more",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = C3,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = getAllNewData) { house ->
                            CustomItem(house = house, navController)
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarD(navController: NavController) {

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val items = remember {
        mutableStateListOf(
            "Algiers",
            "Batna",
            "Bejaia"
        )
    }

    SearchBar(
        modifier = Modifier
            .background(C2)
            .padding(if (active) {
                0.dp
            } else {
                16.dp
            })
            .fillMaxWidth(),
        query = text,
        onQueryChange = {
                        text = it
        },
        onSearch = {
            items.add(text)
            active = false
            text = ""
            navController.navigate(BottomBarScreen.Search.route)
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(
                text = "Search",
                color = Color(0x99FFF5F3),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search icon",
                tint = Color(0x99FFF5F3)
                )
        },
        trailingIcon = {
            if(active) {
                Icon(
                    modifier = Modifier.clickable {
                        if(text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color(0x99FFF5F3)
                )
            }
        },
        shape = RoundedCornerShape(14.dp),
        colors = SearchBarDefaults.colors(containerColor = C5),
        windowInsets = WindowInsets(left = 14.dp,top = 20.dp, bottom = 20.dp)
    ) {
        items.forEach {
            Row(modifier = Modifier.padding(all = 14.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Default.Done,
                    contentDescription = "History icon",
                    tint = Color(0x99FFF5F3)
                )
                Text(
                    text = it,
                    color = Color(0x99FFF5F3)
                )
            }
        }
    }
}