package com.example.darckoum.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.screen.common.SearchResultItem


@Composable
fun SearchScreen(
    bottomBarNavHostController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
) {
    val tag = "SearchScreen"
    val announcementsLazyPagingItems = searchViewModel.announcementsFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        searchViewModel.getAnnouncementsByQuery(sharedViewModel.searchQuery.toString())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (announcementsLazyPagingItems.itemCount != 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = announcementsLazyPagingItems.itemCount) { index ->
                    val item = announcementsLazyPagingItems[index]
                    if (item != null) {
                        SearchResultItem(
                            announcement = item,
                            bottomBarNavHostController = bottomBarNavHostController,
                            sharedViewModel = sharedViewModel
                        )
                    } else {
                        Log.d(tag, "item number $index is null in popular lazy row")
                    }
                }
            }
        } else {
            Text(
                text = "There's nothing to show",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}