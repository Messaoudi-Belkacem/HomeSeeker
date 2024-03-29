package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.darckoum.ui.screen.MainScreen
import com.example.darckoum.ui.screen.WelcomeScreen
import com.example.darckoum.ui.screen.add.AddViewModel
import com.example.darckoum.ui.screen.login.LoginScreen
import com.example.darckoum.ui.screen.login.LoginViewModel
import com.example.darckoum.ui.screen.register.RegisterScreen
import com.example.darckoum.ui.screen.register.RegisterViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    addViewModel: AddViewModel
) {
    val time = 500
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        enterTransition = { fadeIn(animationSpec = tween(time)) },
        exitTransition = { fadeOut(animationSpec = tween(time)) },
        popEnterTransition = { fadeIn(animationSpec = tween(time)) },
        popExitTransition = { fadeOut(animationSpec = tween(time)) }
    ) {
        composable(
            route = Screen.Welcome.route,

        ) {
            WelcomeScreen(navController = navController)
        }
        composable(
            route = Screen.LogIn.route
        ) {
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }
        composable(
            route = Screen.Main.route
        ) {
            MainScreen(addViewModel)
        }
    }
}