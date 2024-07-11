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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darckoum.MainViewModel
import com.example.darckoum.R
import com.example.darckoum.data.state.ProfileState
import com.example.darckoum.navigation.Graph
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.screen.common.LoadingDialog
import com.example.darckoum.screen.common.OutlinedTextFieldSample
import com.example.darckoum.screen.common.OwnedAnnouncementItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    bottomBarNavHostController: NavHostController,
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val tag = "ProfileScreen"
    var areFieldsEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val showDialog = profileViewModel.showDialog.value
    val profileState by profileViewModel.profileState

    val ownedAnnouncementsLazyPagingItems = profileViewModel.ownedAnnouncementsFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        profileViewModel.getUserDetails()
        profileViewModel.getMyAnnouncements(mainViewModel.token.value.toString())
    }
    val context = LocalContext.current

    when(profileState) {
        is ProfileState.Error -> {
            Toast.makeText(
                context,
                (profileState as ProfileState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            Log.d(tag, (profileState as ProfileState.Error).toString())
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    onClick = { profileViewModel.getUserDetails() }
                ) {
                    Text(
                        text = "Retry",
                        fontSize = 14.sp
                    )
                }
            }
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
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
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
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(bottom = padding.calculateBottomPadding())
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AvatarAndDecorationConstraintLayoutContent()
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextFieldSample(
                                    label = "Username",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = profileViewModel.usernameTextFieldText,
                                    onValueChange = {
                                        profileViewModel.usernameTextFieldText.value = it
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        capitalization = KeyboardCapitalization.Words
                                    ),
                                    enabled = false
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.
                                    fillMaxWidth()
                                ) {
                                    OutlinedTextFieldSample(
                                        label = "First name",
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f),
                                        text = profileViewModel.firstNameTextFieldText,
                                        onValueChange = {
                                            profileViewModel.firstNameTextFieldText.value = it
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            capitalization = KeyboardCapitalization.Words
                                        ),
                                        enabled = areFieldsEnabled
                                    )
                                    OutlinedTextFieldSample(
                                        label = "Last name",
                                        modifier = Modifier
                                            .fillMaxWidth(1f),
                                        text = profileViewModel.lastNameTextFieldText,
                                        onValueChange = {
                                            profileViewModel.lastNameTextFieldText.value = it
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            capitalization = KeyboardCapitalization.Words
                                        ),
                                        enabled = areFieldsEnabled
                                    )
                                }
                                OutlinedTextFieldSample(
                                    label = "Phone number",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = profileViewModel.phoneTextFieldText,
                                    onValueChange = {
                                        if (it.isDigitsOnly()) {
                                            profileViewModel.phoneTextFieldText.value = it
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    enabled = areFieldsEnabled
                                )
                            }
                            Text(
                                text = "My announcements",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            when(ownedAnnouncementsLazyPagingItems.loadState.refresh) {
                                is LoadState.Loading -> {
                                    Box(
                                        modifier = Modifier
                                            .height(172.dp)
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(64.dp),
                                        )
                                    }
                                }
                                is LoadState.Error -> {
                                    Toast.makeText(
                                        context,
                                        (ownedAnnouncementsLazyPagingItems.loadState.refresh as LoadState.Error).error.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d(tag, (ownedAnnouncementsLazyPagingItems.loadState.refresh as LoadState.Error).error.toString())
                                    Box(
                                        modifier = Modifier
                                            .height(180.dp)
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Button(onClick = { profileViewModel.getMyAnnouncements(mainViewModel.token.value.toString()) }) {
                                            Text(text = "Retry")
                                        }
                                    }
                                }
                                else -> {
                                    LazyRow(
                                        modifier = Modifier
                                            .height(180.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        contentPadding = PaddingValues(start = 16.dp)
                                    ) {
                                        items(count = ownedAnnouncementsLazyPagingItems.itemCount) { index ->
                                            val item = ownedAnnouncementsLazyPagingItems[index]
                                            if (item != null) {
                                                OwnedAnnouncementItem(
                                                    announcement = item,
                                                    bottomBarNavHostController = bottomBarNavHostController,
                                                    sharedViewModel = sharedViewModel
                                                )
                                            } else {
                                                Log.d(tag, "item number $index is null")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
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
                                        profileViewModel.patchUserDetails()
                                        areFieldsEnabled = !areFieldsEnabled
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(48.dp)
                                ) {
                                    Text(
                                        text = "Save",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
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
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(48.dp)
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
    if (showDialog) {
        LoadingDialog("Logging out, please wait ")
    }
}

@Composable
fun AvatarAndDecorationConstraintLayoutContent() {
    ConstraintLayout {
        val (decoration, avatar) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.profile_screen_decoration),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(decoration) { top.linkTo(parent.top) }
        )
        Image(
            painter = painterResource(id = R.drawable.profile_avatar),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(decoration.bottom, margin = (-78).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}