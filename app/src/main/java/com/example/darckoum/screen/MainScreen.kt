package com.example.darckoum.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.darckoum.MainViewModel
import com.example.darckoum.navigation.HomeNavGraph
import com.example.darckoum.navigation.screen.BottomBarScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
    mainViewModel: MainViewModel
) {
    val bottomBarNavHostController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(bottomBarNavHostController = bottomBarNavHostController) },
        content = { paddingValues ->
            HomeNavGraph(
                bottomBarNavHostController = bottomBarNavHostController,
                navHostController = navHostController,
                sharedViewModel = sharedViewModel,
                paddingValues = paddingValues,
                mainViewModel = mainViewModel
            )
        }
    )
}

@Composable
fun BottomBar(bottomBarNavHostController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Add,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by bottomBarNavHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 8.dp)
                .background(MaterialTheme.colorScheme.surface/*Color.Transparent*/),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    bottomBarNavHostController = bottomBarNavHostController
                )
            }
        }
    }

}

@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    bottomBarNavHostController: NavHostController
) {
    val tag = "AddItem"
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else Color.Unspecified
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = backgroundColor)
            .clickable(onClick = {
                Log.d(tag, "Bottom navigation bar item button clicked")
                bottomBarNavHostController.navigate(screen.route)
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = screen.icon),
            contentDescription = "icon"
        )
    }
}