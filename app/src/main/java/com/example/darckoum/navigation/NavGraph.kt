package com.example.darckoum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.darckoum.ui.screen.MainScreen
import com.example.darckoum.ui.screen.WelcomeScreen
import com.example.darckoum.ui.screen.add.AddViewModel
import com.example.darckoum.ui.screen.login.LoginScreen
import com.example.darckoum.ui.screen.login.LoginViewModel
import com.example.darckoum.ui.screen.profile.ProfileViewModel
import com.example.darckoum.ui.screen.register.RegisterScreen
import com.example.darckoum.ui.screen.register.RegisterViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    addViewModel: AddViewModel,
    profileViewModel: ProfileViewModel,
    startDestination: String
) {
    val time = 500
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(animationSpec = tween(time), initialOffsetX = {fullWidth ->  
            -fullWidth
        }) },
        exitTransition = { slideOutHorizontally(animationSpec = tween(time), targetOffsetX = {fullWidth ->
            fullWidth
        }) },
        popEnterTransition = { slideInHorizontally(animationSpec = tween(time), initialOffsetX = {fullWidth ->
            -fullWidth
        }) },
        popExitTransition = { slideOutHorizontally(animationSpec = tween(time), targetOffsetX = {fullWidth ->
            fullWidth
        }) }
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
            MainScreen(navController, addViewModel, profileViewModel)
        }
    }
}