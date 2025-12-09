package com.suvojeet.gauravactstudio

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.ui.components.BookingDialog
import com.suvojeet.gauravactstudio.ui.components.BottomNavigationBar
import com.suvojeet.gauravactstudio.util.EmailService
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp

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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    var showBookingDialog by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showBookingDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.EditCalendar, contentDescription = null) },
                text = { Text("Book Now") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )

        if (showBookingDialog) {
            BookingDialog(
                packageName = "General Inquiry",
                isSubmitting = isSubmitting,
                isSuccess = isSuccess,
                onDismiss = {
                    showBookingDialog = false
                    isSuccess = false // Reset success state
                },
                onSubmit = { name, phone, eventType, otherEventType, date, eventTime, eventAddress, notes, location ->
                    scope.launch {
                        isSubmitting = true
                        try {
                            EmailService().sendEmail(
                                name = name,
                                phone = phone,
                                eventType = eventType,
                                otherEventType = otherEventType,
                                date = date,
                                eventTime = eventTime,
                                eventAddress = eventAddress,
                                notes = notes,
                                packageName = "General Inquiry",
                                location = location
                            )
                            isSuccess = true // Trigger success animation
                            // Wait for animation to play before dismissing
                            kotlinx.coroutines.delay(1500)
                            showBookingDialog = false
                            snackbarHostState.showSnackbar("Booking request sent successfully!")
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar(e.message ?: "Failed to send request")
                        } finally {
                            isSubmitting = false
                            isSuccess = false // Reset for next time (though dialog is closed)
                        }
                    }
                }
            )
        }
    }
}
