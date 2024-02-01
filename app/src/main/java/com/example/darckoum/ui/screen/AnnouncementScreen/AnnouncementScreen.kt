package com.example.darckoum.ui.screen.AnnouncementScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.darckoum.R
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C5

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementScreen(
    houseRepository: HouseRepository
) {

    val house = houseRepository.getAllData()[2]

    val titleTextFieldText by remember { mutableStateOf(house.title) }
    val propertyTypeTextFieldText by remember { mutableStateOf(house.propertyType) }
    val priceTextFieldText by remember { mutableIntStateOf(house.price) }
    val locationTextFieldText by remember { mutableStateOf(house.location) }
    val stateTextFieldText by remember { mutableStateOf("Batna") }
    var descriptionTextFieldText by remember { mutableStateOf(house.description) }

    Column(
        modifier = Modifier
            .background(C2)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp, top = 35.dp)
            .fillMaxSize(),
    ) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f)
        ) {

            val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })

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
                userScrollEnabled = selectedImageUris.isNotEmpty()
            ) { index ->
                AsyncImage(
                    model = selectedImageUris[index],
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = titleTextFieldText,
                color = Color(0x99FFF5F3),
                modifier = Modifier
                    .fillMaxWidth(1f),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
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
                    text = propertyTypeTextFieldText.description,
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
                    text = priceTextFieldText.toString(),
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
                    text = "State",
                    color = Color(0x99FFF5F3),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                )
                Text(
                    text = stateTextFieldText,
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
                    text = "Address",
                    color = Color(0x99FFF5F3),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                )
                Text(
                    text = locationTextFieldText,
                    color = Color(0x99FFF5F3),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                )
            }
            DescriptionOutlinedTextField(
                label = "Description",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 10,
                minLines = 1,
                text = descriptionTextFieldText,
                onValueChange = {
                    descriptionTextFieldText = it
                }
            )
        }
    }
}

@Composable
fun DescriptionOutlinedTextField(
    label: String,
    modifier: Modifier,
    maxLines: Int,
    minLines: Int,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedLabelColor = Color(0x99FFF5F3),
            unfocusedBorderColor = C5,
            unfocusedTextColor = Color(0x99FFF5F3)
        ),
        maxLines = maxLines,
        minLines = minLines,
        shape = RoundedCornerShape(14.dp),
        readOnly = true
    )
}

@Preview
@Composable
fun AnnouncementScreenPreview() {
    AnnouncementScreen(houseRepository = HouseRepository())
}