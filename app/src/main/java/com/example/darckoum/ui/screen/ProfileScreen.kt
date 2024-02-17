package com.example.darckoum.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.darckoum.R
import com.example.darckoum.ui.theme.C2
import com.example.darckoum.ui.theme.C3
import com.example.darckoum.ui.theme.C5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    var areFieldsEnabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_screen_background),
            contentDescription = "Background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    title = {
                        Text(
                            text = "Profile",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = C3
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null,
                                tint = C3
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { areFieldsEnabled = !areFieldsEnabled },
                            enabled = !areFieldsEnabled
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = C3
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),

                    )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SimpleOutlinedTextFieldSample(
                            label = "Username",
                            modifier = Modifier
                                .fillMaxWidth(),
                            enabled = !areFieldsEnabled,
                            preText = "wail05"
                        )
                        SimpleOutlinedTextFieldSample(
                            label = "Email address",
                            modifier = Modifier
                                .fillMaxWidth(),
                            enabled = !areFieldsEnabled,
                            preText = "wailmessaoudi806@gmail.com"
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SimpleOutlinedTextFieldSample(
                                label = "First name",
                                modifier = Modifier
                                    .fillMaxWidth(0.5f),
                                enabled = !areFieldsEnabled,
                                preText = "Belkacem"
                            )
                            SimpleOutlinedTextFieldSample(
                                label = "Last name",
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                enabled = !areFieldsEnabled,
                                preText = "Messaoudi"
                            )
                        }
                        GenderRow(selectedGender = Gender.MALE, enabled = areFieldsEnabled)
                        SimpleOutlinedTextFieldSample(
                            label = "Phone number",
                            modifier = Modifier
                                .fillMaxWidth(),
                            enabled = !areFieldsEnabled,
                            preText = "0664813680"
                        )
                    }
                    Button(
                        onClick = {
                            areFieldsEnabled = !areFieldsEnabled
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                        contentPadding = PaddingValues(vertical = 20.dp),
                        enabled = areFieldsEnabled,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun SimpleOutlinedTextFieldSample(
    label: String,
    modifier: Modifier,
    enabled: Boolean,
    preText: String
    // onTextChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(preText) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            // onTextChanged(it)
        },
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
        maxLines = 1,
        shape = RoundedCornerShape(14.dp),
        readOnly = enabled
    )
}

@Composable
fun GenderRow(selectedGender: Gender/*, onGenderSelected: (Gender) -> Unit*/, enabled: Boolean) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val genders = Gender.values()

        genders.forEach { gender ->
            RadioButton(
                selected = selectedGender == gender,
                onClick = { /*onGenderSelected(gender) */},
                modifier = Modifier
                    .selectable(selected = selectedGender == gender) {},
                enabled = enabled
            )

            Text(
                text = gender.description,
                color = Color(0x99FFF5F3),
                modifier = Modifier
            )
        }
    }
}

enum class Gender(val description: String) {
    MALE("Male"),
    FEMALE("Female")
}