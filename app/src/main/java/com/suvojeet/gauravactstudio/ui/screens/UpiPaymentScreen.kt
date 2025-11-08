package com.suvojeet.gauravactstudio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.material3.OutlinedTextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpiPaymentScreen(navController: NavController) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var selectedUpiApp by remember { mutableStateOf<UpiApp?>(null) }
    val upiId = "9354654066@ptaxis"
    val payeeName = "Gourav Act Studio"

    val upiApps = listOf(
        UpiApp("Google Pay", "com.google.android.apps.nbu.paisa.user", Color(0xFF4285F4)),
        UpiApp("PhonePe", "com.phonepe.app", Color(0xFF5F259F)),
        UpiApp("Paytm", "net.one97.paytm", Color(0xFF00B9F5)),
        UpiApp("BHIM UPI", "in.org.npci.upiapp", Color(0xFFFF6B00))
    )

    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "payment_icon")
    val iconScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "UPI Payment",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE0F7FA), // Light Cyan
                            Color(0xFFFADADD), // Light Pink
                            Color(0xFFFFF9C4)  // Light Yellow
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // item { Spacer(modifier = Modifier.height(8.dp)) } // <-- YEH WALA SPACE REMOVE KAR DIYA HAI

                // Animated Payment Icon
                item {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(12.dp, CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Payment,
                            contentDescription = "UPI Payment",
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer {
                                    scaleX = iconScale
                                    scaleY = iconScale
                                },
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Title
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Quick & Secure",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "UPI Payment",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Amount Input Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Enter Amount",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF424242)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = amount,
                                onValueChange = { newValue ->
                                    if (newValue.all { it.isDigit() || it == '.' } && 
                                        newValue.count { it == '.' } <= 1) {
                                        amount = newValue
                                    }
                                },
                                label = { Text("₹ Amount in INR") },
                                placeholder = { Text("0.00") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    cursorColor = MaterialTheme.colorScheme.primary
                                ),
                                textStyle = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )

                            if (amount.isNotBlank() && amount.toDoubleOrNull() != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Amount: ₹${amount}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.secondary, // Use secondary for accent
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // UPI App Selection
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Select Payment App",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            upiApps.forEach { app ->
                                UpiAppItem(
                                    app = app,
                                    isSelected = selectedUpiApp == app,
                                    onSelect = { selectedUpiApp = app }
                                )
                                if (app != upiApps.last()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }

                // Payment Info
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f) // Themed light background
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            PaymentInfoRow("Payee", payeeName)
                            Spacer(modifier = Modifier.height(8.dp))
                            PaymentInfoRow("UPI ID", upiId)
                        }
                    }
                }

                // Pay Button
                item {
                    val isEnabled = amount.isNotBlank() && 
                                   amount.toDoubleOrNull() != null && 
                                   selectedUpiApp != null

                    Button(
                        onClick = {
                            if (isEnabled) {
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
                                    println("Error launching UPI app: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .shadow(if (isEnabled) 12.dp else 4.dp, RoundedCornerShape(32.dp)),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = isEnabled
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Payment,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Pay Now",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun UpiAppItem(
    app: UpiApp,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) app.brandColor.copy(alpha = 0.12f) else Color.Transparent,
        label = "background_color"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) app.brandColor else Color(0xFFE0E0E0),
        label = "border_color"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(app.brandColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = Color(0xFF212121),
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(
                visible = isSelected,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = app.brandColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF757575),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF212121),
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class UpiApp(
    val name: String, 
    val packageName: String,
    val brandColor: Color = Color(0xFF6200EA)
)

@Preview(showBackground = true)
@Composable
fun UpiPaymentScreenPreview() {
    GauravActStudioTheme {
        UpiPaymentScreen(navController = rememberNavController())
    }
}

