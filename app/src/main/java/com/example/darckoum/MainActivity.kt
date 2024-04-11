package com.example.darckoum

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.darckoum.data.repository.DataStoreRepository
import com.example.darckoum.data.repository.Repository
import com.example.darckoum.navigation.Screen
import com.example.darckoum.navigation.SetupNavGraph
import com.example.darckoum.ui.screen.SharedViewModel
import com.example.darckoum.ui.screen.add.AddViewModel
import com.example.darckoum.ui.screen.add.AddViewModelFactory
import com.example.darckoum.ui.screen.announcement.AnnouncementViewModel
import com.example.darckoum.ui.screen.announcement.AnnouncementViewModelFactory
import com.example.darckoum.ui.screen.login.LoginViewModel
import com.example.darckoum.ui.screen.login.LoginViewModelFactory
import com.example.darckoum.ui.screen.profile.ProfileViewModel
import com.example.darckoum.ui.screen.profile.ProfileViewModelFactory
import com.example.darckoum.ui.screen.register.RegisterViewModel
import com.example.darckoum.ui.screen.register.RegisterViewModelFactory
import com.example.darckoum.util.TokenUtil
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val tag = "MainActivity"

    private val activityScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var addViewModel: AddViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var announcementViewModel: AnnouncementViewModel
    private lateinit var sharedViewModel: SharedViewModel

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        val repository = Repository()

        val registerViewModelFactory = RegisterViewModelFactory(repository,application)
        val loginViewModelFactory = LoginViewModelFactory(repository,application)
        val addViewModelFactory = AddViewModelFactory(repository,application)
        val profileViewModelFactory = ProfileViewModelFactory(repository, application)
        val announcementViewModelFactory = AnnouncementViewModelFactory(repository)

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        var token = ""
        val context = this.applicationContext

        runBlocking {
            token = withContext(Dispatchers.IO) {
                DataStoreRepository.TokenManager.getToken(context).toString()
            }
        }

        Log.d(tag, "token : $token")

        val tokenIsExpired = TokenUtil.isTokenExpired(token)
        val startDestination: String = if (tokenIsExpired || token.isBlank()) {
            Screen.Welcome.route
        } else Screen.Main.route

        setContent {

            registerViewModel = ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]
            loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
            addViewModel = ViewModelProvider(this, addViewModelFactory)[AddViewModel::class.java]
            profileViewModel = ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]
            announcementViewModel = ViewModelProvider(this, announcementViewModelFactory)[AnnouncementViewModel::class.java]
            sharedViewModel = viewModel()

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
                    addViewModel = addViewModel,
                    profileViewModel = profileViewModel,
                    startDestination = startDestination,
                    announcementViewModel = announcementViewModel,
                    sharedViewModel = sharedViewModel
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