package com.example.darckoum.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darckoum.MainViewModel
import com.example.darckoum.R
import com.example.darckoum.data.state.HomeState
import com.example.darckoum.data.state.LoginState
import com.example.darckoum.items.CustomItem
import com.example.darckoum.navigation.screen.BottomBarScreen

@Composable
fun HomeScreen(
    bottomBarNavHostController: NavController,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {

    val tag = "HomeScreen"
    val announcementsByDiscoverLazyPagingItems = homeViewModel.announcementsFlow.collectAsLazyPagingItems()
    val homeState = homeViewModel.homeState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.getAnnouncementsByDiscover(mainViewModel.token.value.toString())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                SearchBarD(bottomBarNavHostController)
        },
        content = { paddingValues ->
            when (announcementsByDiscoverLazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "Hang tight...",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                is LoadState.Error -> {
                    Toast.makeText(
                        context,
                        (announcementsByDiscoverLazyPagingItems.loadState.refresh as LoadState.Error).error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(tag, (announcementsByDiscoverLazyPagingItems.loadState.refresh as LoadState.Error).error.toString())
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Button(onClick = { homeViewModel.getAnnouncementsByDiscover(mainViewModel.token.value.toString()) }) {
                            Text(text = "Retry")
                        }
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Discover",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            if(announcementsByDiscoverLazyPagingItems.itemCount == 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(280.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "There's nothing to show",
                                        modifier = Modifier
                                    )
                                }

                            } else {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                ) {
                                    items(count = announcementsByDiscoverLazyPagingItems.itemCount) { index ->
                                        val item = announcementsByDiscoverLazyPagingItems[index]
                                        if (item != null) {
                                            CustomItem(announcement = item, navHostController = navHostController)
                                        } else {
                                            Log.d(tag, "item number $index is null")
                                        }

                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Popular",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            LazyRow(
                                modifier = Modifier.padding(start = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                /*items(items = getAllNewData) { announcement ->
                                    CustomItem(announcement = announcement, navController)
                                }*/
                            }
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
            .padding(
                if (active) {
                    0.dp
                } else {
                    16.dp
                }
            )
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