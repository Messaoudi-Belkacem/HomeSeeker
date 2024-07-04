package com.example.darckoum.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                    )
                Text(
                    text = "Darckoum",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                    )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    /*navController.navigate(route = Screen.LogIn.route)*/
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                contentPadding = PaddingValues(horizontal = 140.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Next",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
