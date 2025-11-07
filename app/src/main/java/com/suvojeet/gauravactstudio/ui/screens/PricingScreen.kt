package com.suvojeet.gauravactstudio.ui.screens

import android.util.Log // Error logging ke liye import
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.components.InquiryDialog
import com.suvojeet.gauravactstudio.util.EmailService
import kotlinx.coroutines.launch

data class PricePackage(
    val name: String,
    val price: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
    val icon: ImageVector = Icons.Filled.PhotoCamera
)

 @Composable
fun PricingScreen(modifier: Modifier = Modifier) {
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
    var showInquiryDialog by remember { mutableStateOf(false) }
    var selectedPackage by remember { mutableStateOf("") }
    var isSubmittingInquiry by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val emailService = remember { EmailService() }
    val snackbarHostState = remember { SnackbarHostState() }

    if (showInquiryDialog) {
        InquiryDialog(
            packageName = selectedPackage,
            isSubmitting = isSubmittingInquiry,
            onDismiss = {
                if (!isSubmittingInquiry) {
                    showInquiryDialog = false
                }
            },
            onSubmit = { name, phone, eventType, otherEventType, date, notes ->
                coroutineScope.launch {
                    isSubmittingInquiry = true
                    try {
                        emailService.sendEmail(
                            name = name,
                            phone = phone,
                            eventType = eventType,
                            otherEventType = otherEventType,
                            date = date,
                            notes = notes,
                            packageName = selectedPackage
                        )
                        snackbarHostState.showSnackbar(
                            message = "Inquiry sent successfully! We will contact you soon.",
                            duration = SnackbarDuration.Short
                        )
                        showInquiryDialog = false // Dialog ko success par band karein

                    } catch (e: Exception) {
                        Log.e("PricingScreen", "Failed to send email", e)
                        snackbarHostState.showSnackbar(
                            message = e.message ?: "An unknown error occurred.",
                            duration = SnackbarDuration.Long
                        )
                    } finally {
                        isSubmittingInquiry = false
                    }
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
                .padding(innerPadding)
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
                modifier = Modifier.fillMaxSize()
            ) {
                // Header Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedContent(isVisible) {
                            Icon(
                                imageVector = Icons.Filled.Payments,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        AnimatedContent(isVisible, delay = 100) {
                            Text(
                                text = stringResource(R.string.pricing_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        AnimatedContent(isVisible, delay = 200) {
                            Text(
                                text = stringResource(R.string.pricing_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Pricing Cards
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(pricingList) { index, pricePackage ->
                        AnimatedContent(isVisible, delay = 300L + (index * 75L)) {
                            PricePackageCard(pricePackage = pricePackage, onChoosePlan = {
                                selectedPackage = it
                                showInquiryDialog = true
                            })
                        }
                    }

                    // Custom Package Card at the end
                    item {
                        AnimatedContent(isVisible, delay = 600L) {
                            CustomPackageCard(onContact = {
                                selectedPackage = it
                                showInquiryDialog = true
                            })
                        }
                    }
                }
            }
        }
    }
}

 @Composable
fun PricePackageCard(pricePackage: PricePackage, modifier: Modifier = Modifier, onChoosePlan: (String) -> Unit) {
    val borderModifier = if (pricePackage.isPopular) {
        Modifier.border(
            width = 3.dp,
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

 @Composable
fun CustomPackageCard(onContact: (String) -> Unit) {
    val customPackageTitle = stringResource(R.string.pricing_custom_package_title)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))           
            Text(
                text = customPackageTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = stringResource(R.string.pricing_custom_package_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedButton(
                onClick = { onContact(customPackageTitle) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.pricing_contact_for_custom_quote),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
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