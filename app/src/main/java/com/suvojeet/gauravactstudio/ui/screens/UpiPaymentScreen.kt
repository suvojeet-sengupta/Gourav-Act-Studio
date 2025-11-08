package com.suvojeet.gauravactstudio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpiPaymentScreen(navController: NavController) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var selectedUpiApp by remember { mutableStateOf<UpiApp?>(null) }
    val upiId = "9354654066@ptaxis" // User's UPI ID
    val payeeName = "Gourav Act Studio" // Payee name

    val upiApps = listOf(
        UpiApp("Google Pay", "com.google.android.apps.nbu.paisa.user"),
        UpiApp("PhonePe", "com.phonepe.app"),
        UpiApp("Paytm", "net.one97.paytm"),
        UpiApp("BHIM UPI", "in.org.npci.upiapp")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pay via UPI") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE0F7FA),
                            Color(0xFFFADADD),
                            Color(0xFFFFF9C4)
                        )
                    )
                )
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Icon(
                imageVector = Icons.Filled.Payment,
                contentDescription = "UPI Payment",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter Amount to Pay",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() || it == '.' } && newValue.count { it == '.' } <= 1) {
                        amount = newValue
                    }
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select UPI App",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .heightIn(max = 200.dp) // Limit height for scrollability
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(upiApps) { app ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedUpiApp = app }
                            .background(if (selectedUpiApp == app) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedUpiApp == app,
                            onClick = { selectedUpiApp = app },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = app.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (amount.isNotBlank() && amount.toDoubleOrNull() != null && selectedUpiApp != null) {
                        val encodedUpiId = URLEncoder.encode(upiId, StandardCharsets.UTF_8.toString())
                        val encodedPayeeName = URLEncoder.encode(payeeName, StandardCharsets.UTF_8.toString())
                        val encodedAmount = URLEncoder.encode(amount, StandardCharsets.UTF_8.toString())

                        val uri = Uri.parse("upi://pay?pa=$encodedUpiId&pn=$encodedPayeeName&am=$encodedAmount&cu=INR")
                        val intent = Intent(Intent.ACTION_VIEW, uri)

                        selectedUpiApp?.packageName?.let { packageName ->
                            intent.setPackage(packageName)
                        }

                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Handle case where UPI app is not installed or intent fails
                            // You might want to show a Snackbar or AlertDialog here
                            println("Error launching UPI app: ${e.message}")
                        }
                    } else {
                        // Show error message if amount is empty or no app selected
                        println("Please enter amount and select a UPI app.")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = amount.isNotBlank() && amount.toDoubleOrNull() != null && selectedUpiApp != null
            ) {
                Text(
                    text = "Pay Now",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Payment,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

data class UpiApp(val name: String, val packageName: String)

@Preview(showBackground = true)
@Composable
fun UpiPaymentScreenPreview() {
    GauravActStudioTheme {
        UpiPaymentScreen(navController = NavController(LocalContext.current))
    }
}
