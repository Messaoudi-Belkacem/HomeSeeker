package com.example.darckoum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.navigation.SetupNavGraph
import com.example.darckoum.ui.screen.add.AddViewModel
import com.example.darckoum.ui.screen.add.AddViewModelFactory
import com.example.darckoum.ui.screen.login.LoginViewModel
import com.example.darckoum.ui.screen.login.LoginViewModelFactory
import com.example.darckoum.ui.screen.register.RegisterViewModel
import com.example.darckoum.ui.screen.register.RegisterViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var addViewModel: AddViewModel

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        val repository = Repository()

        val registerViewModelFactory = RegisterViewModelFactory(repository,application)
        val loginViewModelFactory = LoginViewModelFactory(repository,application)
        val addViewModelFactory = AddViewModelFactory(repository,application)

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            registerViewModel = ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]
            loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
            addViewModel = ViewModelProvider(this, addViewModelFactory)[AddViewModel::class.java]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(R.drawable.onboarding_screen_background),
                        contentScale = ContentScale.FillBounds
                )
            ) {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    registerViewModel = registerViewModel,
                    loginViewModel = loginViewModel,
                    addViewModel = addViewModel
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