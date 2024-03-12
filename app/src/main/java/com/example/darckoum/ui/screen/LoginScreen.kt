package com.example.darckoum.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.darckoum.R
import com.example.darckoum.navigation.Screen
import com.example.darckoum.ui.theme.C1
import com.example.darckoum.ui.theme.C2

@Composable
fun WelcomeScreen2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
        /**
        .paint(
        painter = painterResource(R.drawable.onboarding_screen_background),
        contentScale = ContentScale.FillBounds
        )
         */
    ) {
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
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .padding(top = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(1f)
            ) {
                LoginScreenOutlinedTextFieldSample(
                    label = "Email",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    text = "",
                    onValueChange = {},
                    temp = C1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginScreenOutlinedTextFieldSample(
                    label = "Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    text = "",
                    onValueChange = {},
                    temp = C1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Forgot password?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = C1,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        navController.navigate(route = Screen.Main.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
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
                        color = C1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sign Up",
                        color = C1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}


@Composable
fun LoginScreenOutlinedTextFieldSample(
    label: String,
    modifier: Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    temp: Color,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = text,
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

@Preview
@Composable
fun LoginScreenOutlinedTextFieldSamplePreview() {
    LoginScreenOutlinedTextFieldSample(
        label = "Email",
        modifier = Modifier,
        text = "",
        onValueChange = {},
        temp = C2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}