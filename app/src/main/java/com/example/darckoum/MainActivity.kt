package com.example.darckoum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.darckoum.data.repository.HouseRepository
import com.example.darckoum.navigation.SetupNavGraph
import com.example.darckoum.ui.screen.AnnouncementScreen.AnnouncementViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val viewModel: AnnouncementViewModel by viewModels()



    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(R.drawable.onboarding_screen_background),
                        contentScale = ContentScale.FillBounds
                )
            ) {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)

                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                DisposableEffect(systemUiController) {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false,
                        isNavigationBarContrastEnforced = false
                    )
                    // setStatusBarColor() and setNavigationBarColor() also exist
                    onDispose {}
                }
            }
        }
    }
}