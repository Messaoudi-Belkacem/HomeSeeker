package com.example.darckoum.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.darckoum.navigation.Screen

@Composable
fun WelcomeScreen2(
                   navController: NavController
) {
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
                .fillMaxHeight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Easily find real estate ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " in the Maghreb region ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "for rent or purchase",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 18.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(route = Screen.Main.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7BD73)),
                    contentPadding = PaddingValues(vertical = 20.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                ) {
                    Text(
                        text = "Start",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x884F4F4F)),
                    contentPadding = PaddingValues(vertical = 20.dp),
                    border = BorderStroke(width = 2.dp, color = Color(0xFF4F4F4F)),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                ) {
                    Text(
                        text = "Return",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}