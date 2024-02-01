package com.example.darckoum.items

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.darckoum.R
import com.example.darckoum.data.model.Announcement
import com.example.darckoum.navigation.BottomBarScreen
import com.example.darckoum.ui.theme.C1
import com.example.darckoum.ui.theme.C3
import com.example.darckoum.ui.theme.C5

@Composable
fun CustomItem(announcement: Announcement, navController: NavController) {
    val formattedPrice = formatPrice(announcement.price)
    val housePicture: Int = when (announcement.id) {
        0 -> R.drawable.house1photo
        1 -> R.drawable.house2photo
        2 -> R.drawable.house3photo
        3 -> R.drawable.house4photo
        4 -> R.drawable.house5photo
        5 -> R.drawable.house6photo
        else -> R.drawable.house_default_pic
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .border(width = 2.dp, color = C5, shape = RoundedCornerShape(14.dp))
            .background(Color(0x994F4F4F))
            .padding(16.dp)
            .clickable {
                navController.navigate(route = BottomBarScreen.Announcement.route)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(housePicture),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 178.dp, height = 150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Text(
                text = announcement.title,
                color = C3,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(45.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        tint = C3
                    )
                    Text(
                        text = announcement.location,
                        color = C3,
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
                        tint = C3
                    )
                    Text(
                        text = announcement.propertyType.description,
                        color = C3,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Text(
                text = "$formattedPrice DZD",
                color = C1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CustomSearchItem(announcement: Announcement, navController: NavController) {
    val formattedPrice = formatPrice(announcement.price)
    val housePicture: Int = when (announcement.id) {
        0 -> R.drawable.house1photo
        1 -> R.drawable.house2photo
        2 -> R.drawable.house3photo
        3 -> R.drawable.house4photo
        4 -> R.drawable.house5photo
        5 -> R.drawable.house6photo
        else -> R.drawable.house_default_pic
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.96f)
            .clip(RoundedCornerShape(14.dp))
            .border(width = 2.dp, color = C5, shape = RoundedCornerShape(14.dp))
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
                Image(
                    painter = painterResource(housePicture),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(width = 150.dp, height = 100.dp),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = announcement.title,
                    color = C3,
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
                        tint = C3
                    )
                    Text(
                        text = announcement.location,
                        color = C3,
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
                        tint = C3
                    )
                    Text(
                        text = announcement.propertyType.description,
                        color = C3,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = "$formattedPrice DZD",
                    color = C1,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

fun formatPrice(price: Int): String {
    val priceString = price.toString()
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