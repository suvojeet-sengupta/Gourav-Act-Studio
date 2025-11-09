package com.suvojeet.gauravactstudio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.suvojeet.gauravactstudio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpiPaymentScreen(navController: NavController) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var selectedUpiApp by remember { mutableStateOf<UpiApp?>(null) }
    val upiId = "paytmqr5m791w@ptys"
    val payeeName = "GAURAV KUMAR"

    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccessSnackbar by remember { mutableStateOf(false) }
    var showFailureSnackbar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val paymentResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val response = data?.getStringExtra("response")
        if (response != null) {
            val status = response.split("&").associate {
                val parts = it.split("=")
                parts[0] to parts.getOrElse(1) { "" }
            }["Status"]

            if (status == "SUCCESS") {
                showSuccessSnackbar = true
            } else {
                showFailureSnackbar = true
            }
        } else {
            showSuccessSnackbar = true
        }
    }

    val upiApps = listOf(
        UpiApp("Google Pay", "com.google.android.apps.nbu.paisa.user", Color(0xFF4285F4), R.drawable.gpay_icon),
        UpiApp("PhonePe", "com.phonepe.app", Color(0xFF5F259F), R.drawable.phonepe_icon),
        UpiApp("Paytm", "net.one97.paytm", Color(0xFF00B9F5), R.drawable.paytm_icon),
        UpiApp("BHIM UPI", "in.org.npci.upiapp", Color(0xFFFF6B00), R.drawable.bhim_icon)
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

    LaunchedEffect(showSuccessSnackbar) {
        if (showSuccessSnackbar) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Payment Successful!",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
                showSuccessSnackbar = false
            }
        }
    }

    LaunchedEffect(showFailureSnackbar) {
        if (showFailureSnackbar) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Payment Failed or Cancelled.",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
                showFailureSnackbar = false
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFAFAFA),
                            Color(0xFFFFFBFE),
                            Color(0xFFF5F5F7)
                        )
                    )
                )
        ) {
            // Decorative background
            PaymentDecorativeBackground()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }

                // Animated Payment Icon
                item {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(
                                elevation = 16.dp,
                                shape = CircleShape,
                                ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f),
                                spotColor = Color(0xFF8B5CF6).copy(alpha = 0.3f)
                            )
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFEC4899),
                                        Color(0xFF8B5CF6)
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
                            tint = Color.White
                        )
                    }
                }

                // Title
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Quick & Secure",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A1A1A)
                        )
                        Text(
                            text = "UPI Payment",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF666666),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Amount Input Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.15f)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF8B5CF6).copy(alpha = 0.05f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(0xFFEC4899),
                                                        Color(0xFF8B5CF6)
                                                    )
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "₹",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Enter Amount",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                OutlinedTextField(
                                    value = amount,
                                    onValueChange = { newValue ->
                                        if (newValue.all { it.isDigit() || it == '.' } &&
                                            newValue.count { it == '.' } <= 1) {
                                            amount = newValue
                                        }
                                    },
                                    label = { Text("Amount in INR") },
                                    placeholder = { Text("0.00") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFEC4899),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        focusedLabelColor = Color(0xFFEC4899),
                                        cursorColor = Color(0xFFEC4899)
                                    ),
                                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                )

                                if (amount.isNotBlank() && amount.toDoubleOrNull() != null) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Total: ₹${amount}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color(0xFF8B5CF6),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // UPI App Selection
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = Color(0xFFEC4899).copy(alpha = 0.15f)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFEC4899).copy(alpha = 0.05f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(0xFF06B6D4),
                                                        Color(0xFF0EA5E9)
                                                    )
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountBalance,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Select Payment App",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                upiApps.forEach { app ->
                                    ModernUpiAppItem(
                                        app = app,
                                        isSelected = selectedUpiApp == app,
                                        onSelect = { selectedUpiApp = app }
                                    )
                                    if (app != upiApps.last()) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Payment Info Card - ENHANCED
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = Color(0xFF06B6D4).copy(alpha = 0.15f)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF06B6D4).copy(alpha = 0.05f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(0xFF06B6D4),
                                                        Color(0xFF0EA5E9)
                                                    )
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Info,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Payment Details",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A1A1A)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                ModernPaymentInfoRow(
                                    icon = Icons.Filled.Person,
                                    label = "Payee Name",
                                    value = payeeName,
                                    iconColor = Color(0xFFEC4899)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                ModernPaymentInfoRow(
                                    icon = Icons.Filled.AccountBalanceWallet,
                                    label = "UPI ID",
                                    value = upiId,
                                    iconColor = Color(0xFF8B5CF6)
                                )
                            }
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
                                val amountDouble = amount.toDoubleOrNull() ?: 0.0
                                val formattedAmount = String.format("%.2f", amountDouble)
                                val encodedAmount = URLEncoder.encode(formattedAmount, StandardCharsets.UTF_8.toString())

                                val intent = Intent(Intent.ACTION_VIEW)
                                val appPackage = selectedUpiApp?.packageName

                                val baseUri = "upi://pay?pa=$encodedUpiId&am=$encodedAmount&cu=INR"
                                val uriString: String

                                if (appPackage == "com.phonepe.app") {
                                    val transactionId = "TID" + System.currentTimeMillis()
                                    val transactionNote = "Payment for service"
                                    val encodedTr = URLEncoder.encode(transactionId, StandardCharsets.UTF_8.toString())
                                    val encodedTn = URLEncoder.encode(transactionNote, StandardCharsets.UTF_8.toString())
                                    uriString = "$baseUri&tr=$encodedTr&tn=$encodedTn"
                                } else {
                                    uriString = baseUri
                                }

                                intent.data = Uri.parse(uriString)
                                appPackage?.let { intent.setPackage(it) }

                                try {
                                    paymentResultLauncher.launch(intent)
                                } catch (e: Exception) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Could not find the selected UPI app. Please try another.",
                                            actionLabel = "Dismiss",
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .shadow(
                                elevation = if (isEnabled) 16.dp else 4.dp,
                                shape = RoundedCornerShape(32.dp),
                                ambientColor = if (isEnabled) Color(0xFFEC4899).copy(alpha = 0.3f) else Color.Transparent
                            ),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color(0xFFE0E0E0)
                        ),
                        contentPadding = PaddingValues(0.dp),
                        enabled = isEnabled
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = if (isEnabled) {
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFFEC4899),
                                                Color(0xFF8B5CF6)
                                            )
                                        )
                                    } else {
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFFE0E0E0),
                                                Color(0xFFE0E0E0)
                                            )
                                        )
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Payment,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Pay Now",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun ModernUpiAppItem(
    app: UpiApp,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) app.brandColor.copy(alpha = 0.08f) else Color(0xFFF8F9FA),
        label = "background_color"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) app.brandColor else Color(0xFFE0E0E0),
        label = "border_color"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable { onSelect() },
        shape = RoundedCornerShape(20.dp),
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
            // App Icon with original logo
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = app.iconRes),
                    contentDescription = app.name,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = app.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(
                visible = isSelected,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(app.brandColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ModernPaymentInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1A1A1A),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaymentDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Background Animation")

    val offsetX1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset X"
    )

    val offsetY1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Pink gradient orb
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (120 + offsetX1).dp, y = (-80 + offsetY1).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x40EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Purple gradient orb
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150 - offsetX1).dp, y = (350 - offsetY1).dp)
                .blur(120.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x358B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Cyan gradient orb
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 80.dp, y = (650 + offsetY1).dp)
                .blur(110.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x3006B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )
    }
}

data class UpiApp(
    val name: String,
    val packageName: String,
    val brandColor: Color = Color(0xFF6200EA),
    val iconRes: Int // Add icon resource ID
)

@Preview(showBackground = true)
@Composable
fun UpiPaymentScreenPreview() {
    GauravActStudioTheme {
        UpiPaymentScreen(navController = rememberNavController())
    }
}
