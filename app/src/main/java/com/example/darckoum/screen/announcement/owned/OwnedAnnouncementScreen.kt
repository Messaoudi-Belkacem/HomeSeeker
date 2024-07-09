package com.example.darckoum.screen.announcement.owned

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = announcementResponse.title,
                            color = Color(0x99FFF5F3),
                            modifier = Modifier
                                .fillMaxWidth(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            modifier = Modifier
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(35.dp)
                        ) {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.room_icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp),
                                    tint = Color(0x99FFF5F3)
                                )
                                Text(
                                    text = announcementResponse.numberOfRooms.toString(),
                                    color = Color(0x99FFF5F3),
                                    modifier = Modifier,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.area_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(width = 32.dp, height = 24.dp),
                                    tint = Color(0x99FFF5F3)
                                )
                                Text(
                                    text = announcementResponse.area.toString() + "m²",
                                    color = Color(0x99FFF5F3),
                                    modifier = Modifier,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            Text(
                                text = "Type",
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            )
                            Text(
                                text = announcementResponse.propertyType,
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            Text(
                                text = "Price",
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            )
                            Text(
                                text = ownedAnnouncementViewModel.getFormattedPrice(announcementResponse.price) + " DZD",
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                fontSize = 16.sp
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            Text(
                                text = "State",
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            )
                            Text(
                                text = announcementResponse.state,
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            Text(
                                text = "Location",
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            )
                            Text(
                                text = announcementResponse.location,
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            Text(
                                text = "Description",
                                color = Color(0x99FFF5F3),
                                modifier = Modifier,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = announcementResponse.description,
                                color = Color(0x99FFF5F3),
                                modifier = Modifier
                            )
                        }
                    }
                    Button(
                        onClick = {
                            ownedAnnouncementViewModel.deleteAnnouncement(announcementId = announcementResponse.id)
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        contentPadding = PaddingValues(vertical = 20.dp),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Delete",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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

