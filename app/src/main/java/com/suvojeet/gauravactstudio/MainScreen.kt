package com.suvojeet.gauravactstudio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.AppNavHost
import com.suvojeet.gauravactstudio.ui.components.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
