package com.example.darckoum.ui.screen.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.darckoum.R
import com.example.darckoum.navigation.Screen
import com.example.darckoum.ui.theme.C1
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    val usernameState = remember { mutableStateOf("") }
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val responseIsSuccessfulState = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    /*val snackBarHostState = remember { SnackbarHostState() }*/

    Scaffold(
        /*snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },*/
        containerColor = Color.Transparent
    ) {it
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 64.dp),
                contentAlignment = Alignment.Center
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
                        color = C1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
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
                    LoginScreenOutlinedTextFieldSample(
                        label = "Username",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        text = usernameState,
                        onValueChange = { usernameState.value = it },
                        temp = C1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginScreenOutlinedTextFieldSample(
                        label = "First Name",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        text = firstNameState,
                        onValueChange = { firstNameState.value = it },
                        temp = C1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginScreenOutlinedTextFieldSample(
                        label = "Last Name",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        text = lastNameState,
                        onValueChange = { lastNameState.value = it },
                        temp = C1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LoginScreenOutlinedTextFieldSample(
                        label = "Password",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        text = passwordState,
                        onValueChange = { passwordState.value = it },
                        temp = C1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if (
                                usernameState.value.isBlank() ||
                                firstNameState.value.isBlank() ||
                                lastNameState.value.isBlank() ||
                                passwordState.value.isBlank()
                            ) {
                                Toast.makeText(context, "This is a Toast. Yay!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                scope.launch {
                                    responseIsSuccessfulState.value = registerViewModel.registerUser(
                                        usernameState.value,
                                        firstNameState.value,
                                        lastNameState.value,
                                        passwordState.value
                                    )
                                    if (responseIsSuccessfulState.value) {
                                        /*scope.launch {
                                            snackBarHostState.showSnackbar("Registration successful! Welcome to our community.")
                                        }*/
                                        navController.navigate(route = Screen.Main.route)
                                        /*{
                                            popUpTo(Screen.Main.route) {
                                                inclusive = true
                                            }
                                        }*/
                                    } else {
                                        /*scope.launch {
                                            snackBarHostState.showSnackbar("Oops! Something went wrong during registration.")
                                        }*/
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = C1),
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
                            color = C1,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Sign in",
                            color = C1,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(route = Screen.LogIn.route) {
                                        popUpTo(Screen.LogIn.route) {
                                            inclusive = true
                                        }
                                    }
                                    Log.d("Log in screen", "Sign up text clicked")
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreenOutlinedTextFieldSample(
    label: String,
    modifier: Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    temp: Color,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = text.value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedLabelColor = temp,
            focusedLabelColor = temp,
            focusedBorderColor = temp,
            unfocusedBorderColor = temp,
            focusedTextColor = temp,
            unfocusedTextColor = temp,
            focusedPlaceholderColor = temp,
            unfocusedPlaceholderColor = temp,
            focusedSupportingTextColor = temp,
            unfocusedSupportingTextColor = temp,
            cursorColor = temp
        ),
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = keyboardOptions
    )
}
