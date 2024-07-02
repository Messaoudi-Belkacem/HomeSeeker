package com.example.darckoum

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.navigation.Graph
import com.example.darckoum.navigation.RootNavigationGraph
import com.example.darckoum.ui.screen.SharedViewModel
import com.example.darckoum.ui.theme.AppTheme
import com.example.darckoum.util.TokenUtil
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"
    lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        var token: String
        val tokenIsValid: Boolean
        val context = this.applicationContext

        runBlocking {
            token = withContext(Dispatchers.IO) {
                DataStoreRepository.TokenManager.getToken(context).toString()
            }
            tokenIsValid = mainViewModel.checkToken(token).value
        }

        Log.d(tag, "token : $token")

        val tokenIsExpired = TokenUtil.isTokenExpired(token)
        val startDestination: String = if (tokenIsExpired || token.isBlank() || tokenIsValid) {
            Graph.AUTHENTICATION
        } else {
            Graph.HOME
        }

        setContent {
            AppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(R.drawable.onboarding_screen_background),
                            contentScale = ContentScale.FillBounds
                        )
                        .imePadding()
                ) {
                    RootNavigationGraph(
                        startDestination = Graph.AUTHENTICATION
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