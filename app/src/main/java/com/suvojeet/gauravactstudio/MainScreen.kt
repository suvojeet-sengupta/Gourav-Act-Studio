package com.suvojeet.gauravactstudio

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.ui.components.BottomNavigationBar
import kotlin.math.abs

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            // Permissions granted, you can now access coarse location
        } else {
            // Permissions denied
        }
    }

    LaunchedEffect(Unit) {
        val areGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!areGranted) {
            launcher.launch(permissions)
        }
    }
    val navController = rememberNavController()

    val screens = listOf(
        Screen.Home,
        Screen.Services,
        Screen.Gallery,
        Screen.Pricing,
        Screen.About
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    var dragAmount by remember { mutableStateOf(0f) }
    var swiped by remember { mutableStateOf(false) }

    val swipeableModifier = Modifier.pointerInput(currentRoute) {
        detectHorizontalDragGestures(
            onDragStart = {
                dragAmount = 0f
                swiped = false
            },
            onHorizontalDrag = { change, delta ->
                if (!swiped) {
                    dragAmount += delta
                    val screenWidth = size.width
                    val swipeThreshold = screenWidth / 4

                    if (abs(dragAmount) > swipeThreshold) {
                        swiped = true
                        val currentIndex = screens.indexOfFirst { it.route == currentRoute }
                        if (currentIndex != -1) {
                            if (dragAmount < 0) { // Swipe left
                                if (currentIndex < screens.size - 1) {
                                    val nextScreen = screens[currentIndex + 1]
                                    navController.navigate(nextScreen.route)
                                }
                            } else { // Swipe right
                                if (currentIndex > 0) {
                                    val previousScreen = screens[currentIndex - 1]
                                    navController.navigate(previousScreen.route)
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding).then(swipeableModifier)
        )
    }
}
