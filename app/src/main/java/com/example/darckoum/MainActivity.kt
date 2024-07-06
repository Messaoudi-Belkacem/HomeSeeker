package com.example.darckoum

import android.os.Bundle
import android.util.Log
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.darckoum.navigation.RootNavigationGraph
import com.example.darckoum.screen.SharedViewModel
import com.example.darckoum.ui.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"
    private val sharedViewModel: SharedViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate method is called")
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                mainViewModel.condition.value
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(R.drawable.onboarding_screen_background),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    RootNavigationGraph(
                        startDestination = mainViewModel.startDestination.value,
                        sharedViewModel = sharedViewModel,
                        mainViewModel = mainViewModel
                    )
                    val systemUiController = rememberSystemUiController()
                    DisposableEffect(systemUiController) {
                        systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = false,
                            isNavigationBarContrastEnforced = false
                        )
                        onDispose {}
                    }
                }
            }
        }
    }
}