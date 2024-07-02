package com.example.darckoum.ui.screen.login

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
import androidx.compose.material3.ButtonDefaults
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.darckoum.R
import com.example.darckoum.data.state.LoginState
import com.example.darckoum.navigation.Graph
import com.example.darckoum.navigation.screen.AuthenticationScreen
import com.example.darckoum.ui.screen.common.OutlinedTextFieldSample
import com.example.darckoum.util.KeyboardAware
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val tag = "LoginScreen"
    val scrollState = rememberScrollState()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val scope = rememberCoroutineScope()
    val loginState by loginViewModel.loginState
    val context = LocalContext.current

    LaunchedEffect(key1 = keyboardHeight) {
        scope.launch {
            scrollState.animateScrollTo(keyboardHeight)
        }
    }

    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    KeyboardAware {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { contentPadding ->
            when (loginState) {
                is LoginState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
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
                is LoginState.Success -> {
                    navHostController.navigate(Graph.HOME)
                }
                is LoginState.Error -> {
                    Toast.makeText(
                        context,
                        (loginState as LoginState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    loginViewModel.setLoginState(LoginState.Initial)
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(contentPadding)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 64.dp)
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
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(start = 18.dp, end = 18.dp, top = 32.dp, bottom = 64.dp)
                                .fillMaxWidth(1f)
                                .fillMaxHeight(1f)
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
                            Spacer(modifier = Modifier.height(32.dp))
                            OutlinedTextFieldSample(
                                label = "Password",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp),
                                text = passwordState,
                                onValueChange = { passwordState.value = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                text = "Forgot password?",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    val username = usernameState.value
                                    val password = passwordState.value
                                    if (
                                        username.isBlank() || password.isBlank()
                                    ) {
                                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                    } else {
                                        scope.launch {
                                            loginViewModel.loginUser(username, password)
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(64.dp)
                            ) {
                                Text(
                                    text = "Login",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(64.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Text(
                                    text = "Don't have an account?",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Sign Up",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .clickable {
                                            Log.d(tag, "Sign up text clicked")
                                            navHostController.navigate(route = AuthenticationScreen.SignUp.route)
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