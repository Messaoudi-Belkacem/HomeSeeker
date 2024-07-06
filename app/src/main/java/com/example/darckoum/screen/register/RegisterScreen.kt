package com.example.darckoum.screen.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.darckoum.R
import com.example.darckoum.data.state.RegistrationState
import com.example.darckoum.navigation.screen.AuthenticationScreen
import com.example.darckoum.screen.common.OutlinedTextFieldSample
import com.example.darckoum.util.KeyboardAware
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val tag = "RegisterScreen"
    val scrollState = rememberScrollState()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val scope = rememberCoroutineScope()
    val registrationState by registerViewModel.registrationState
    LaunchedEffect(key1 = keyboardHeight) {
        scope.launch {
            scrollState.animateScrollTo(keyboardHeight)
        }
    }
    val usernameState = remember { mutableStateOf("") }
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }

    KeyboardAware {
        Scaffold(
            containerColor = Color.Transparent
        ) { paddingValues ->
            val context = LocalContext.current
            when (registrationState) {
                is RegistrationState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
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
                is RegistrationState.Success -> {
                    Log.d(tag, "Sign up was successful, returning to login page")
                    navHostController.popBackStack()
                    navHostController.navigate(AuthenticationScreen.LogIn.route)
                }
                is RegistrationState.Error -> {
                    Toast.makeText(
                        context,
                        (registrationState as RegistrationState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    registerViewModel.setRegistrationState(RegistrationState.Initial)
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(paddingValues = paddingValues)
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_with_no_background),
                                    contentDescription = null,
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(128.dp)
                                )
                                Spacer(modifier = Modifier.height(64.dp))
                                Text(
                                    text = "Easily find real estate in the Maghreb region for rent or purchase",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 32.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(horizontal = 18.dp)
                                    .fillMaxWidth(1f)
                            ) {
                                OutlinedTextFieldSample(
                                    label = "Username",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    text = usernameState,
                                    onValueChange = { usernameState.value = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                OutlinedTextFieldSample(
                                    label = "First Name",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    text = firstNameState,
                                    onValueChange = { firstNameState.value = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                OutlinedTextFieldSample(
                                    label = "Last Name",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    text = lastNameState,
                                    onValueChange = { lastNameState.value = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                OutlinedTextFieldSample(
                                    label = "Password",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    text = passwordState,
                                    onValueChange = { passwordState.value = it },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                OutlinedTextFieldSample(
                                    label = "Phone number",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    text = phoneState,
                                    onValueChange = {
                                        if (it.isDigitsOnly()) {
                                            phoneState.value = it
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Button(
                                    onClick = {
                                        val username = usernameState.value
                                        val firstName = firstNameState.value
                                        val lastName = lastNameState.value
                                        val password = passwordState.value
                                        val phone = phoneState.value
                                        if (
                                            username.isBlank() ||
                                            firstName.isBlank() ||
                                            lastName.isBlank() ||
                                            password.isBlank() ||
                                            phone.isBlank()
                                        ) {
                                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                        } else {
                                            scope.launch {
                                                registerViewModel.registerUser(username, firstName, lastName, password, phone)
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(64.dp)
                                ) {
                                    Text(
                                        text = "Register",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth(1f)
                                ) {
                                    Text(
                                        text = "Already have an account?",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Sign in",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier
                                            .clickable {
                                                Log.d(tag, "Sign in text clicked")
                                                navHostController.popBackStack()
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}