package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.components.BookingDialog
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import java.util.Locale
import com.suvojeet.gauravactstudio.ui.screens.CreateOwnPackage
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.collectAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

data class PricePackage(
    val name: String,
    val price: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
    val icon: ImageVector = Icons.Filled.PhotoCamera
)

 @Composable
fun PricingScreen(
    modifier: Modifier = Modifier,
    viewModel: PricingViewModel = viewModel()
) {
    val pricingList = listOf(
        PricePackage(
            name = stringResource(R.string.pricing_package_standard_name),
            price = "₹12,000",
            features = stringArrayResource(id = R.array.pricing_package_standard_features).toList(),
            gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
            icon = Icons.Filled.CameraAlt
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_deluxe_name),
            price = "₹25,000",
            features = stringArrayResource(id = R.array.pricing_package_deluxe_features).toList(),
            isPopular = true,
            gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
            icon = Icons.Filled.Videocam
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_premium_name),
            price = "₹35,000",
            features = stringArrayResource(id = R.array.pricing_package_premium_features).toList(),
            gradient = listOf(Color(0xFFEC4899), Color(0xFFF97316)),
            icon = Icons.Filled.Movie
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_elite_name),
            price = "₹55,000",
            features = stringArrayResource(id = R.array.pricing_package_elite_features).toList(),
            gradient = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
            icon = Icons.Filled.Diamond
        )
    )
    var isVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.onSnackbarShown()
        }
    }

    if (uiState.showBookingDialog) { // Changed from showInquiryDialog
        BookingDialog( // Changed from InquiryDialog
            packageName = uiState.selectedPackage,
            isSubmitting = uiState.isSubmittingInquiry,
            onDismiss = { viewModel.onDismissBookingDialog() }, // Changed function call
            onSubmit = { name, phone, eventType, otherEventType, date, notes, location ->
                viewModel.onSubmitInquiry(name, phone, eventType, otherEventType, date, notes, location)
            }
        )
    }

    // New: Booking Confirmation Dialog
    AnimatedVisibility(
        visible = uiState.showBookingConfirmation,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissBookingDialog() },
            title = { Text(stringResource(R.string.booking_inquiry_sent_success)) },
            text = {
                Column {
                    Text(stringResource(R.string.booking_confirmation_message))
                    uiState.bookingRequestNumber?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${stringResource(R.string.booking_request_number_label)} $it",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.onDismissBookingDialog() }) {
                    Text("OK")
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        delay(50)
        isVisible = true
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8F9FA),
                            Color(0xFFE9ECEF)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var showCustomBuilder by remember { mutableStateOf(false) }

                // Header Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedContent(isVisible) {
                            Icon(
                                imageVector = Icons.Filled.Payments,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        AnimatedContent(isVisible, delay = 100) {
                            Text(
                                text = stringResource(R.string.pricing_title),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        AnimatedContent(isVisible, delay = 200) {
                            Text(
                                text = stringResource(R.string.pricing_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp)) // Added spacer
                        // Toggle Button
                        Button(
                            onClick = { showCustomBuilder = !showCustomBuilder },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Icon(
                                imageVector = if (showCustomBuilder) Icons.Filled.List else Icons.Filled.Build,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (showCustomBuilder) "View Predefined Packages" else "Build Your Own Package",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Content based on toggle
                if (showCustomBuilder) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()) // Use verticalScroll for custom builder
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        CreateOwnPackage(onContact = { title, details ->
                            viewModel.onChoosePlan(title, details)
                        })
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        itemsIndexed(pricingList) { index, pricePackage ->
                            AnimatedContent(isVisible, delay = 300L + (index * 75L)) {
                                PricePackageCard(pricePackage = pricePackage, onChoosePlan = {
                                    viewModel.onChoosePlan(it)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

 @Composable
fun PricePackageCard(pricePackage: PricePackage, modifier: Modifier = Modifier, onChoosePlan: (String) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "popular_animation")
    val animatedBorderWidth by infiniteTransition.animateFloat(
        initialValue = 3f,
        targetValue = if (pricePackage.isPopular) 5f else 3f, // Animate border width
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "popular_border_width"
    )

    val borderModifier = if (pricePackage.isPopular) {
        Modifier.border(
            width = animatedBorderWidth.dp, // Use animated width
            brush = Brush.linearGradient(pricePackage.gradient),
            shape = RoundedCornerShape(24.dp)
        )
    } else Modifier

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(borderModifier),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (pricePackage.isPopular) 16.dp else 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            // Popular Badge
            if (pricePackage.isPopular) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(pricePackage.gradient)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.pricing_most_popular),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Icon and Package Name
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.linearGradient(pricePackage.gradient)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = pricePackage.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = pricePackage.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.pricing_perfect_for_needs),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Price
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = pricePackage.price,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.pricing_onwards),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Features
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pricePackage.features.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(pricePackage.gradient)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CTA Button
            Button(
                onClick = { onChoosePlan(pricePackage.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pricePackage.isPopular) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp
                )
            ) {
                Text(
                    text = if (pricePackage.isPopular) stringResource(R.string.pricing_choose_this_plan) else stringResource(R.string.pricing_get_started),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

    @Preview(showBackground = true)
  @Composable
 fun PricingScreenPreview() {
     GauravActStudioTheme {
         PricingScreen()
     }
 }
