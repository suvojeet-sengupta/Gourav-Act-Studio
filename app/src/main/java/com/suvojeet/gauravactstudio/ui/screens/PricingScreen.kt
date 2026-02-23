package com.suvojeet.gauravactstudio.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.components.BookingDialog
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay

data class PricePackage(
    val name: String,
    val price: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
    val icon: ImageVector = Icons.Filled.PhotoCamera,
    val category: String = "Wedding" // Default category
)

@Composable
fun PricingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PricingViewModel = viewModel()
) {
    // Data with categories
    val pricingList = listOf(
        PricePackage(
            name = stringResource(R.string.pricing_package_standard_name),
            price = "₹12,000",
            features = stringArrayResource(id = R.array.pricing_package_standard_features).toList(),
            gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
            icon = Icons.Filled.CameraAlt,
            category = "Wedding"
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_deluxe_name),
            price = "₹25,000",
            features = stringArrayResource(id = R.array.pricing_package_deluxe_features).toList(),
            isPopular = true,
            gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
            icon = Icons.Filled.Videocam,
            category = "Wedding"
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_premium_name),
            price = "₹35,000",
            features = stringArrayResource(id = R.array.pricing_package_premium_features).toList(),
            gradient = listOf(Color(0xFFEC4899), Color(0xFFF97316)),
            icon = Icons.Filled.Movie,
            category = "Events"
        ),
        PricePackage(
            name = stringResource(R.string.pricing_package_elite_name),
            price = "₹55,000",
            features = stringArrayResource(id = R.array.pricing_package_elite_features).toList(),
            gradient = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
            icon = Icons.Filled.Diamond,
            category = "Wedding"
        )
        // Add more dummy packages for other categories if needed to visualize filtering
    )

    var selectedCategory by remember { mutableStateOf("All") }
    var isVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Filtering logic
    val filteredPricingList = pricingList.filter { 
        selectedCategory == "All" || it.category == selectedCategory 
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.onSnackbarShown()
        }
    }

    if (uiState.showBookingDialog) {
        BookingDialog(
            packageName = uiState.selectedPackage,
            isSubmitting = uiState.isSubmittingInquiry,
            onDismiss = { viewModel.onDismissBookingDialog() },
            onSubmit = { name, phone, eventType, otherEventType, date, eventTime, eventAddress, notes, location ->
                viewModel.onSubmitInquiry(name, phone, eventType, otherEventType, date, eventTime, eventAddress, notes, location)
            }
        )
    }

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
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F5F9))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                var showCustomBuilder by remember { mutableStateOf(false) }
                var showUpiDevelopmentDialog by remember { mutableStateOf(false) }

                // Sticky Header / Top Bar Area
                Surface(
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        // Title Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.pricing_header_title),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1F2937)
                            )
                        }

                        // Category Chips
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                             FilterChip(
                                selected = selectedCategory == "All",
                                onClick = { selectedCategory = "All" },
                                label = stringResource(R.string.pricing_category_all)
                            )
                            FilterChip(
                                selected = selectedCategory == "Events",
                                onClick = { selectedCategory = "Events" },
                                label = stringResource(R.string.pricing_category_events)
                            )
                             FilterChip(
                                selected = selectedCategory == "Corporate",
                                onClick = { selectedCategory = "Corporate" },
                                label = stringResource(R.string.pricing_category_corporate)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Actions Row (Custom Package & UPI)
                         Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                             // Toggle Custom Builder Button
                            OutlinedButton(
                                onClick = { showCustomBuilder = !showCustomBuilder },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = if (showCustomBuilder) stringResource(R.string.view_predefined_packages) else "Custom Plan",
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            
                            // Pay via UPI Button
                            Button(
                                onClick = { showUpiDevelopmentDialog = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.pay_via_upi),
                                    fontSize = 12.sp,
                                     maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                if (showUpiDevelopmentDialog) {
                    AlertDialog(
                        onDismissRequest = { showUpiDevelopmentDialog = false },
                        title = { Text("Feature Under Development") },
                        text = { Text("This feature is under development.") },
                        confirmButton = {
                            TextButton(onClick = { showUpiDevelopmentDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

                // Content
                if (showCustomBuilder) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        CreateOwnPackage(onContact = { title, details ->
                            viewModel.onChoosePlan(title, details)
                        })
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(filteredPricingList) { index, pricePackage ->
                            AnimatedContent(isVisible, delay = 100L + (index * 50L)) {
                                ModernPricePackageCard(pricePackage = pricePackage, onChoosePlan = {
                                    viewModel.onChoosePlan(it)
                                })
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernPricePackageCard(pricePackage: PricePackage, modifier: Modifier = Modifier, onChoosePlan: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Header with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Brush.horizontalGradient(pricePackage.gradient))
            ) {
                 // Decorative Circles
                Box(modifier = Modifier.offset(x = (-20).dp, y = (-20).dp).size(80.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)))
                Box(modifier = Modifier.offset(x = 300.dp, y = 40.dp).size(100.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)))

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                         // Popular Badge
                        if (pricePackage.isPopular) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "POPULAR",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = pricePackage.gradient[0]
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp)) // Spacer to keep layout consistent
                        }
                        
                         Icon(
                            imageVector = pricePackage.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Text(
                        text = pricePackage.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Column(modifier = Modifier.padding(20.dp)) {
                // Price
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = pricePackage.price,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1F2937)
                    )
                    Text(
                        text = stringResource(R.string.pricing_onwards),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFE5E7EB))

                Spacer(modifier = Modifier.height(16.dp))

                // Features
                pricePackage.features.forEach { feature ->
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF10B981), // Green check
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4B5563)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onChoosePlan(pricePackage.name) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = pricePackage.gradient[0])
                    ) {
                        Text(stringResource(R.string.pricing_choose_this_plan), fontWeight = FontWeight.SemiBold)
                    }
                    
                    val context = LocalContext.current
                    OutlinedButton(
                        onClick = {
                            val shareText = "Check out the '${pricePackage.name}' package from Gaurav Act Studio! Price: ${pricePackage.price}. Features: ${pricePackage.features.joinToString(", ")}. Learn more: [App Download Link/Website]"
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Pricing Package"))
                        },
                        modifier = Modifier
                            .size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFD1D5DB)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF6B7280),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PricingScreenPreview() {
    GauravActStudioTheme {
        PricingScreen(navController = rememberNavController())
    }
}