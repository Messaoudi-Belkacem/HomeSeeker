package com.example.darckoum.ui.screen.announcement

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.darckoum.R
import com.example.darckoum.data.model.request.AnnouncementResponse
import com.example.darckoum.ui.screen.SharedViewModel
import com.example.darckoum.ui.theme.C1
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C5


@Composable
fun AnnouncementScreen(
    announcementViewModel: AnnouncementViewModel,
    sharedViewModel: SharedViewModel
) {

    val context = LocalContext.current

    val announcementResponse = sharedViewModel.announcementWithImages

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(C2),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /*
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedImageUri = uri }
        )
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris -> selectedImageUris = uris }
        )
        */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f)
        ) {

            val pagerState = rememberPagerState(pageCount = { announcementResponse?.images?.size!! })

            Image(painter = painterResource(
                id = R.drawable.house6photo),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .align(Alignment.Center)
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                userScrollEnabled = announcementResponse!!.images!!.isNotEmpty()
            ) { index ->
                Image(
                    bitmap = BitmapFactory.decodeByteArray(announcementResponse.images!![index], 0,announcementResponse.images!![index].size).asImageBitmap(),
                    /*model = selectedImageUris[index],*/
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = announcementResponse!!.title.toString(),
                color = Color(0x99FFF5F3),
                modifier = Modifier
                    .fillMaxWidth(1f),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier
                    .background(color = C5, RoundedCornerShape(10.dp))
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
                    color = C1,
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
/*
@Preview(showSystemUi = true)
@Composable
fun AnnouncementScreenPreview() {
    AnnouncementScreen(houseRepository = HouseRepository())
}
*/
