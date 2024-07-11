package com.example.darckoum.screen.announcement.owned

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MeetingRoom
import androidx.compose.material.icons.rounded.SpaceDashboard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.darckoum.R
import com.example.darckoum.data.state.OwnedAnnouncementState
import com.example.darckoum.navigation.Graph
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.util.Constants.Companion.IMAGE_BASE_URL

@Composable
fun OwnedAnnouncementScreen(
    bottomBarNavHostController: NavHostController,
    ownedAnnouncementViewModel: OwnedAnnouncementViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val tag = "AnnouncementScreen.kt"
    val context = LocalContext.current
    val announcementResponse = sharedViewModel.ownedAnnouncement
    val ownedAnnouncementState by ownedAnnouncementViewModel.ownedAnnouncementState
    if (announcementResponse == null) {
        Log.d(tag, "announcementResponse is null")
        Toast.makeText(
            context,
            "announcementResponse is null",
            Toast.LENGTH_SHORT
        ).show()
        bottomBarNavHostController.navigate(Graph.HOME)
    } else {
        LaunchedEffect(Unit) {
            ownedAnnouncementViewModel.incrementAnnouncementViews(announcementResponse.id)
        }
        when(ownedAnnouncementState) {
            is OwnedAnnouncementState.Loading -> {
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

            is OwnedAnnouncementState.Error -> {
                Toast.makeText(
                    context,
                    (ownedAnnouncementState as OwnedAnnouncementState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
                ownedAnnouncementViewModel.setOwnedAnnouncementState(OwnedAnnouncementState.Initial)
            }
            is OwnedAnnouncementState.Initial -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.4f)
                    ) {
                        val pagerState = rememberPagerState(pageCount = { announcementResponse.imageNames.size })
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            userScrollEnabled = true
                        ) { index ->
                            val imageUrl = IMAGE_BASE_URL + announcementResponse.imageNames[index]
                            Log.d(tag, "image url: $imageUrl")
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                error = painterResource(id = R.drawable.darckoum_logo),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.surface
                                        ),
                                        startY = 500f
                                    )
                                )
                        )
                        // Indicator
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            repeat(announcementResponse.imageNames.size) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .height(8.dp)
                                        .width(if (isSelected) 16.dp else 8.dp)
                                        .background(
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = announcementResponse.title,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                modifier = Modifier
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(35.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onSurface,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.MeetingRoom,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(32.dp),
                                    )
                                    Text(
                                        text = announcementResponse.numberOfRooms.toString(),
                                        modifier = Modifier,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onSurface,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.SpaceDashboard,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(32.dp),
                                    )
                                    Text(
                                        text = announcementResponse.area.toString() + "m²",
                                        modifier = Modifier,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "Type",
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = announcementResponse.propertyType,
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "Price",
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = ownedAnnouncementViewModel.getFormattedPrice(
                                        announcementResponse.price
                                    ) + " DZD",
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "State",
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = announcementResponse.state,
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "Location",
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = announcementResponse.location,
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "Description",
                                    modifier = Modifier,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = announcementResponse.description,
                                    modifier = Modifier,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Gray,
                                    softWrap = true
                                )
                            }
                        }
                        Button(
                            onClick = {
                                ownedAnnouncementViewModel.deleteAnnouncement(announcementId = announcementResponse.id)
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(
                                text = "Delete",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            is OwnedAnnouncementState.Success -> {
                Toast.makeText(context, "Announcement deleted successfully", Toast.LENGTH_SHORT).show()
                bottomBarNavHostController.navigateUp()
            }
        }

    }
}

