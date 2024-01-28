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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.example.darckoum.R
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C5

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementScreen(houseRepository: HouseRepository) {

    val house = houseRepository.getAllData()[2]

    var titleTextFieldText by remember { mutableStateOf(house.title) }
    var propertyTypeTextFieldText by remember { mutableStateOf(house.propertyType) }
    var transactionTypeTextFieldText by remember { mutableStateOf(house.transactionType) }
    var priceTextFieldText by remember { mutableStateOf(house.price) }
    var locationTextFieldText by remember { mutableStateOf(house.location) }
    var stateTextFieldText by remember { mutableStateOf("Batna") }
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
            /*
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                item {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                items(selectedImageUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            */
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AddScreenOutlinedTextFieldSample(
                label = "Title",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = titleTextFieldText,
                onValueChange = {
                    titleTextFieldText = it
                    // Todo onTextChanged(it)
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "Property Type",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = propertyTypeTextFieldText.description,
                onValueChange = {
                    titleTextFieldText = it
                    // Todo onTextChanged(it)
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "TransactionType",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = transactionTypeTextFieldText.description,
                onValueChange = {
                    titleTextFieldText = it
                    // Todo onTextChanged(it)
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "Price",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = priceTextFieldText.toString(),
                onValueChange = {
                    if (it.isDigitsOnly()) {
                        priceTextFieldText = it.toInt()
                    }
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "State",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = stateTextFieldText,
                onValueChange = {
                    stateTextFieldText = it
                    // Todo onTextChanged(it)
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "Address",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                minLines = 1,
                text = locationTextFieldText,
                onValueChange = {
                    locationTextFieldText = it
                }
            )
            AddScreenOutlinedTextFieldSample(
                label = "Description",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 10,
                minLines = 6,
                text = descriptionTextFieldText,
                onValueChange = {
                    descriptionTextFieldText = it
                }
            )
        }
    }
}

@Composable
fun AddScreenOutlinedTextFieldSample(
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
            unfocusedContainerColor = C5,
            focusedContainerColor = C5,
            unfocusedLabelColor = Color(0x99FFF5F3),
            focusedLabelColor = Color(0x99FFF5F3),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color(0x99FFF5F3),
            unfocusedTextColor = Color(0x99FFF5F3)
        ),
        maxLines = maxLines,
        minLines = minLines,
        shape = RoundedCornerShape(14.dp),
        readOnly = true
    )
}