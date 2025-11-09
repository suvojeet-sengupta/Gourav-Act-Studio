package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.suvojeet.gauravactstudio.util.Prefs

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(onCompletion: () -> Unit) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Please select your language and grant the necessary permissions to continue.")
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                Prefs.setLanguage(context, "en")
                if (permissionsState.allPermissionsGranted) {
                    onCompletion()
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }) {
                Text("English")
            }
            Button(onClick = {
                Prefs.setLanguage(context, "hi")
                if (permissionsState.allPermissionsGranted) {
                    onCompletion()
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }) {
                Text("Hindi")
            }
        }

        if (permissionsState.allPermissionsGranted) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onCompletion) {
                Text("Continue")
            }
        }
    }
}
