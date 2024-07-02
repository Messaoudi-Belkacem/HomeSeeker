package com.example.darckoum.ui.screen.announcement

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.darckoum.R
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.ui.screen.SharedViewModel
import java.util.Base64


@Composable
fun AnnouncementScreen(
    bottomBarNavHostController: NavController,
    announcementViewModel: AnnouncementViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val tag = "AnnouncementScreen.kt"
    val context = LocalContext.current
    val announcementResponse = sharedViewModel.announcementWithImages
    if (announcementResponse == null) {
        Log.d(tag, "announcementResponse is null")
        Toast.makeText(
            context,
            "announcementResponse is null",
            Toast.LENGTH_SHORT
        ).show()
        bottomBarNavHostController.navigate(BottomBarScreen.Home.route)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.4f)
            ) {
                var announcementHasImages = false
                if (announcementResponse.images != null) {
                    announcementHasImages = true
                }
                if (announcementHasImages) {
                    val pagerState = rememberPagerState(pageCount = { announcementResponse.images?.size ?: 0 })
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        userScrollEnabled = true
                    ) { index ->
                        val encodedByteArrayString = announcementResponse.images?.get(index).toString()
                        val decodedByteArray = Base64.getDecoder().decode(encodedByteArrayString)
                        /*Log.d(tag, "encodedByteArrayString : $decodedByteArrayString")*/
                        Log.d(tag, "byteArray : $decodedByteArray")
                        byteArrayToBitmap(decodedByteArray)?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                /*model = selectedImageUris[index],*/
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    Image(painter = painterResource(
                        id = R.drawable.house6photo),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .align(Alignment.Center)
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
                    text = announcementResponse.title.toString(),
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
                        text = announcementResponse.propertyType.toString(),
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
                        text = announcementViewModel.getFormattedPrice(announcementResponse.price) + " DZD",
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
                        text = announcementResponse.state.toString(),
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
                        text = announcementResponse.location.toString(),
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
                        text = announcementResponse.description.toString(),
                        color = Color(0x99FFF5F3),
                        modifier = Modifier
                    )
                }
            }
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.setData(Uri.parse("tel:0664813680"))
                    startActivity(context, intent, null)
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                contentPadding = PaddingValues(vertical = 20.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Contact the seller",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}