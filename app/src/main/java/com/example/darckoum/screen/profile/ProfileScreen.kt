package com.example.darckoum.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.darckoum.R
import com.example.darckoum.data.state.ProfileState
import com.example.darckoum.navigation.Graph
import com.example.darckoum.screen.common.LoadingDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    bottomBarNavHostController: NavController,
    navHostController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val tag = "ProfileScreen"
    var areFieldsEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val showDialog = profileViewModel.showDialog.value
    val profileState by profileViewModel.profileState

    LaunchedEffect(Unit) {
        profileViewModel.getUserDetails()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        when(profileState) {
            is ProfileState.Error -> {
                Toast.makeText(
                    context,
                    (profileState as ProfileState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(tag, (profileState as ProfileState.Error).toString())
            }
            is ProfileState.Loading -> {
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
            else -> {
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
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    Log.d(tag, "Navigate back button clicked")
                                    if(!bottomBarNavHostController.navigateUp()) {
                                        Log.d(tag, "Failed to navigate back")
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = null,
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
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
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
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SimpleOutlinedTextFieldSample(
                                    label = "Username",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = !areFieldsEnabled,
                                    preText = profileViewModel.userDetails.value?.username ?: "not available"
                                )
                                SimpleOutlinedTextFieldSample(
                                    label = "Phone number",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = !areFieldsEnabled,
                                    preText = profileViewModel.userDetails.value?.phone ?: "not available"
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
                                        preText = profileViewModel.userDetails.value?.firstName ?: "not available"
                                    )
                                    SimpleOutlinedTextFieldSample(
                                        label = "Last name",
                                        modifier = Modifier
                                            .fillMaxWidth(1f),
                                        enabled = !areFieldsEnabled,
                                        preText = profileViewModel.userDetails.value?.lastName ?: "not available"
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                AnimatedVisibility(
                                    visible = areFieldsEnabled,
                                    enter = slideInVertically(
                                        // Start the slide from 40 (pixels) above where the content is supposed to go, to
                                        // produce a parallax effect
                                        initialOffsetY = { +40 }
                                    ) + expandVertically(
                                        expandFrom = Alignment.Top
                                    ) + scaleIn(
                                        // Animate scale from 0f to 1f using the top center as the pivot point.
                                        transformOrigin = TransformOrigin(0.5f, 0f)
                                    ) + fadeIn(initialAlpha = 0.3f),
                                    exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
                                    /*,
                                    enter = expandVertically { 0 } + scaleIn(),
                                    exit = shrinkVertically { 0 } + scaleOut()*/
                                ) {
                                    Button(
                                        onClick = {
                                            areFieldsEnabled = !areFieldsEnabled
                                        },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                                        contentPadding = PaddingValues(vertical = 20.dp),
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
                                Spacer(modifier = Modifier.height(8.dp))
                                AnimatedVisibility(
                                    visible = !areFieldsEnabled,
                                    enter = slideInVertically(
                                        // Start the slide from 40 (pixels) above where the content is supposed to go, to
                                        // produce a parallax effect
                                        initialOffsetY = { -40 }
                                    ) + expandVertically(
                                        expandFrom = Alignment.Top
                                    ) + scaleIn(
                                        // Animate scale from 0f to 1f using the top center as the pivot point.
                                        transformOrigin = TransformOrigin(0.5f, 0f)
                                    ) + fadeIn(initialAlpha = 0.3f),
                                    exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
                                    /*,
                                        enter = expandVertically { 0 } + scaleIn(),
                                        exit = shrinkVertically { 0 } + scaleOut()*/
                                ) {
                                    Button(
                                        onClick = {
                                            scope.launch {
                                                if (profileViewModel.logout()) {
                                                    Toast.makeText(context, "Log out was successful", Toast.LENGTH_SHORT)
                                                        .show()
                                                    navHostController.navigate(route = Graph.AUTHENTICATION)
                                                } else {
                                                    Toast.makeText(context, "Log out was not successful", Toast.LENGTH_SHORT)
                                                        .show()
                                                }
                                            }
                                        },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                        contentPadding = PaddingValues(vertical = 20.dp),
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(horizontal = 12.dp)
                                    ) {
                                        Text(
                                            text = "Log out",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    if (showDialog) {
        LoadingDialog("Logging out, please wait ")
    }
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
        maxLines = 1,
        shape = RoundedCornerShape(14.dp),
        readOnly = enabled
    )
}