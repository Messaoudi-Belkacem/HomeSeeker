package com.example.darckoum.items

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.darckoum.R
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.navigation.Graph
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.util.Constants.Companion.IMAGE_BASE_URL

@Composable
fun CustomItem(announcement: Announcement, bottomBarNavHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val formattedPrice = formatPrice(announcement.price)
    val tag = "CustomItem"
    Box(
        modifier = Modifier
            .size(width = 210.dp, height = 300.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.outlineVariant)
            .border(width = 2.dp, color = Color(0xFF666666), shape = RoundedCornerShape(14.dp))
            .padding(8.dp)
            .clickable {
                sharedViewModel.announcement = announcement
                bottomBarNavHostController.navigate(route = Graph.DETAILS)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = IMAGE_BASE_URL + announcement.imageNames.first()
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
                    .size(width = 180.dp, height = 150.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                text = announcement.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                    )
                    Text(
                        text = announcement.location,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type),
                        contentDescription = null,
                    )
                    Text(
                        text = announcement.propertyType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Text(
                text = "$formattedPrice DZD",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CustomSearchItem(announcement: Announcement, navController: NavController) {
    val tag = "CustomSearchItem"
    val formattedPrice = formatPrice(announcement.price)
    Box(
        modifier = Modifier
            .fillMaxWidth(0.96f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0x994F4F4F))
            .padding(12.dp)
            .clickable {
                navController.navigate(route = BottomBarScreen.Announcement.route)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val imageUrl = IMAGE_BASE_URL + announcement.imageNames.first()
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
                        .size(width = 180.dp, height = 150.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
                Text(
                    text = announcement.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                    )
                    Text(
                        text = announcement.location,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_type),
                        contentDescription = null,
                    )
                    Text(
                        text = announcement.propertyType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = "$formattedPrice DZD",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

fun formatPrice(price: Double): String {
    val priceString = price.toInt().toString()
    val formattedPrice = buildString {
        var count = 0
        for (i in priceString.length - 1 downTo 0) {
            append(priceString[i])
            count++
            if (count % 3 == 0 && i > 0) {
                append(" ")
            }
        }
    }
    return formattedPrice.reversed()
}