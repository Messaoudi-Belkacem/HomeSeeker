package com.example.darckoum.ui.screen.add

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.darckoum.R
import com.example.darckoum.data.model.enum_classes.PropertyType
import com.example.darckoum.data.model.enum_classes.State
import com.example.darckoum.data.state.AddState
import com.example.darckoum.navigation.screen.BottomBarScreen
import com.example.darckoum.ui.screen.SharedViewModel
import com.example.darckoum.ui.screen.common.OutlinedTextFieldSample
import com.example.darckoum.util.KeyboardAware
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    bottomBarNavHostController: NavController,
    sharedViewModel: SharedViewModel,
    addViewModel: AddViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val tag = "AddScreen.kt"
    val scrollState = rememberScrollState()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = keyboardHeight) {
        scope.launch {
            scrollState.animateScrollTo(keyboardHeight)
        }
    }
    val context = LocalContext.current
    val addState by addViewModel.addState
    KeyboardAware {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ,
        ) {
            val modifier: Modifier
            when (addState) {
                is AddState.Loading -> {
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(bottom = 80.dp)
                        .fillMaxSize()
                        .blur(20.dp)
                    AddAnnouncementUI(
                        modifier = modifier,
                        tag = tag,
                        addViewModel = addViewModel,
                        context = context
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .background(color = Color.Transparent),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Hang tight...",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                is AddState.Success -> {
                    bottomBarNavHostController.navigate(BottomBarScreen.Announcement.route)
                    sharedViewModel.addAnnouncementResponse(addViewModel.announcementResponse.value!!)
                }
                is AddState.Error -> {
                    Toast.makeText(
                        context,
                        (addState as AddState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    addViewModel.setAddState(AddState.Initial)
                }
                else -> {
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(bottom = 80.dp)
                        .fillMaxSize()
                    AddAnnouncementUI(
                        modifier = modifier,
                        tag = tag,
                        addViewModel = addViewModel,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun AddAnnouncementUI(
    modifier: Modifier,
    tag: String,
    addViewModel: AddViewModel,
    context: Context
) {

    val titleTextFieldText = remember { mutableStateOf("") }
    val areaTextFieldText = remember { mutableStateOf("") }
    val numberOfRoomsTextFieldText = remember { mutableStateOf("") }
    val priceTextFieldText = remember { mutableStateOf("") }
    val locationTextFieldText = remember { mutableStateOf("") }
    val descriptionTextFieldText = remember { mutableStateOf("") }
    val selectedPropertyTypeText = remember { mutableStateOf("") }
    val selectedStateText = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
    ) {
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris ->
                selectedImageUris = uris
                for (uri in uris) {
                    Log.d(tag, uri.path.toString())
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val pagerState = rememberPagerState(pageCount = { selectedImageUris.size })
            Image(
                painter = painterResource(
                    id = R.drawable.default_image
                ),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize(0.5f)
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
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 14.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Remove an image from the selected images
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable(onClick = {
                            val indexToRemove = pagerState.currentPage
                            if (indexToRemove in selectedImageUris.indices) {
                                val modifiedList = selectedImageUris
                                    .toMutableList()
                                    .apply {
                                        removeAt(indexToRemove)
                                    }
                                selectedImageUris = modifiedList
                            }
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = null,
                    )
                }
                // Image Indicator
                Row(
                    Modifier
                        .wrapContentHeight()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val width = if (pagerState.currentPage == iteration) 18.dp else 8.dp
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(CircleShape)
                                .height(8.dp)
                                .width(width)
                        )
                    }
                }
                // Add Images from gallery
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable(onClick = {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextFieldSample(
                label = "Title",
                modifier = Modifier.fillMaxWidth(),
                text = titleTextFieldText,
                onValueChange = {
                    titleTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            OutlinedTextFieldSample(
                label = "Area",
                modifier = Modifier.fillMaxWidth(),
                text = areaTextFieldText,
                onValueChange = {
                    areaTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextFieldSample(
                label = "Number Of Rooms",
                modifier = Modifier.fillMaxWidth(),
                text = numberOfRoomsTextFieldText,
                onValueChange = {
                    numberOfRoomsTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ExposedDropdownMenuSample(
                options = PropertyType.entries.map { it.description },
                label = "Property type",
                onOptionSelected = { selectedOption ->
                    selectedPropertyTypeText.value = selectedOption
                }
            )
            OutlinedTextFieldSample(
                label = "Price",
                modifier = Modifier.fillMaxWidth(),
                text = priceTextFieldText,
                onValueChange = {
                    priceTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ExposedDropdownMenuSample(
                options = State.entries.map { it.displayName },
                label = "Wilaya",
                onOptionSelected = { selectedOption ->
                    selectedStateText.value = selectedOption
                }
            )
            OutlinedTextFieldSample(
                label = "Location",
                modifier = Modifier.fillMaxWidth(),
                text = locationTextFieldText,
                onValueChange = {
                    locationTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            OutlinedTextFieldSample(
                label = "Description",
                modifier = Modifier.fillMaxWidth(),
                text = descriptionTextFieldText,
                onValueChange = {
                    descriptionTextFieldText.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                minLines = 6,
                maxLines = 10
            )
            Button(
                onClick = {
                    val title = titleTextFieldText.value
                    val area = areaTextFieldText.value.toInt()
                    val numberOfRooms = numberOfRoomsTextFieldText.value.toInt()
                    val propertyType = selectedPropertyTypeText.value
                    val price = priceTextFieldText.value
                    val state = selectedStateText.value
                    val location = locationTextFieldText.value
                    val description = descriptionTextFieldText.value
                    if (
                        title.isBlank() ||
                        price.isBlank() ||
                        location.isBlank() ||
                        description.isBlank()
                    ) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        scope.launch {
                            addViewModel.createAnnouncement(
                                title = title,
                                area = area,
                                numberOfRooms = numberOfRooms,
                                location = location,
                                state = state,
                                propertyType = propertyType,
                                price = price.toDouble(),
                                description = description,
                                owner = "",
                                selectedImageUris = selectedImageUris,
                                context = context
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                contentPadding = PaddingValues(vertical = 20.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Announce",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/*@Composable
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
        maxLines = maxLines,
        minLines = minLines,
        shape = RoundedCornerShape(14.dp),
    )
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuSample(
    options: List<String>,
    label: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            maxLines = 1,
            shape = RoundedCornerShape(14.dp),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onOptionSelected(selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(
                        textColor = Color(0x99FFF5F3)
                    ),
                )
            }
        }
    }
}


