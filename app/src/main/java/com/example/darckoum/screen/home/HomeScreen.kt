package com.example.darckoum.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darckoum.MainViewModel
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.screen.common.CustomItem
import com.example.darckoum.screen.common.SearchBarSampleForHomeScreen

@Composable
fun HomeScreen(
    bottomBarNavHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    paddingValues: PaddingValues
) {
    val tag = "HomeScreen"
    val announcementsByDiscoverLazyPagingItems = homeViewModel.announcementsByDiscoverFlow.collectAsLazyPagingItems()
    val announcementsByPopularLazyPagingItems = homeViewModel.announcementsByPopularFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.getDiscoverAnnouncements(mainViewModel.token.value.toString())
        homeViewModel.getPopularAnnouncements(mainViewModel.token.value.toString())
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                SearchBarSampleForHomeScreen(
                    bottomBarNavHostController = bottomBarNavHostController,
                    sharedViewModel = sharedViewModel
                )
        },
        content = { otherRaddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = otherRaddingValues.calculateTopPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Discover",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
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
                                Button(onClick = { homeViewModel.getDiscoverAnnouncements(mainViewModel.token.value.toString()) }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                        else -> {
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
                                            CustomItem(
                                                announcement = item,
                                                bottomBarNavHostController = bottomBarNavHostController,
                                                sharedViewModel = sharedViewModel
                                            )
                                        } else {
                                            Log.d(tag, "item number $index is null in discover lazy row")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Popular",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    when (announcementsByPopularLazyPagingItems.loadState.refresh) {
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
                                Button(onClick = { homeViewModel.getPopularAnnouncements(mainViewModel.token.value.toString()) }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                        else -> {
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
                                    items(count = announcementsByPopularLazyPagingItems.itemCount) { index ->
                                        val item = announcementsByPopularLazyPagingItems[index]
                                        if (item != null) {
                                            CustomItem(
                                                announcement = item,
                                                bottomBarNavHostController = bottomBarNavHostController,
                                                sharedViewModel = sharedViewModel
                                            )
                                        } else {
                                            Log.d(tag, "item number $index is null in popular lazy row")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
