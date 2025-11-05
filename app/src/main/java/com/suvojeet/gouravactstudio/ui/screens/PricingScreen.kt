package com.suvojeet.gouravactstudio.ui.screens

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
import com.suvojeet.gouravactstudio.ui.theme.GouravActStudioTheme
import kotlinx.coroutines.delay
import com.suvojeet.gouravactstudio.ui.components.AnimatedContent

data class PricePackage(
    val name: String,
    val price: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6)),
    val icon: ImageVector = Icons.Filled.PhotoCamera
)

val pricingList = listOf(
    PricePackage(
        name = "Standard Package",
        price = "₹12,000",
        features = listOf(
            "30-sheet album (200 photos)",
            "32GB pen drive with all photos",
            "2 cameras for photo and video",
            "Basic editing included",
            "Same-day highlights"
        ),
        gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
        icon = Icons.Filled.CameraAlt
    ),
    PricePackage(
        name = "Deluxe Package",
        price = "₹25,000",
        features = listOf(
            "2 professional cameras",
            "1 drone for aerial shots",
            "1-2 cinematic highlight videos",
            "32GB USB with all content",
            "Premium photo album",
            "Advanced color grading"
        ),
        isPopular = true,
        gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
        icon = Icons.Filled.Videocam
    ),
    PricePackage(
        name = "Premium Package",
        price = "₹35,000",
        features = listOf(
            "Cinematic + Traditional video & photo",
            "Multiple highlight videos",
            "2 professional videographers",
            "2 professional photographers",
            "Drone coverage included",
            "Luxury album & USB combo",
            "Fast 7-day delivery"
        ),
        gradient = listOf(Color(0xFFEC4899), Color(0xFFF97316)),
        icon = Icons.Filled.Movie
    ),
    PricePackage(
        name = "Elite Package",
        price = "₹55,000",
        features = listOf(
            "Pre-wedding shoot at location",
            "Ring ceremony complete coverage",
            "Full wedding shoot (Cinematic + Traditional)",
            "3 videographers + 3 photographers",
            "Multiple drones for aerial shots",
            "Premium albums (3 copies)",
            "Same-day photo delivery",
            "Lifetime cloud backup"
        ),
        gradient = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
        icon = Icons.Filled.Diamond
    )
)

 @Composable
fun PricingScreen(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    Box(
        modifier = modifier
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
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    AnimatedContent(isVisible, delay = 200) {
                        Text(
                            text = "Our Pricing Plans",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    AnimatedContent(isVisible, delay = 400) {
                        Text(
                            text = "Choose the perfect package for your special moments",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Pricing Cards
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(pricingList) { index, pricePackage ->
                    AnimatedContent(isVisible, delay = 600L + (index * 150L)) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            PricePackageCard(pricePackage = pricePackage)
                        }
                    }
                }
                
                // Custom Package Card at the end
                item {
                    AnimatedContent(isVisible, delay = 1200L) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CustomPackageCard()
                        }
                    }
                }
            }
        }
    }
}



 @Composable
fun PricePackageCard(pricePackage: PricePackage, modifier: Modifier = Modifier) {
    val borderModifier = if (pricePackage.isPopular) {
        Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(pricePackage.gradient),
            shape = RoundedCornerShape(24.dp)
        )
    } else Modifier

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .then(borderModifier),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (pricePackage.isPopular) 12.dp else 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
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
                            text = "MOST POPULAR",
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
                        text = "Perfect for your needs",
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
                    text = "onwards",
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
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pricePackage.isPopular) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    text = if (pricePackage.isPopular) "Choose This Plan" else "Get Started",
                    fontWeight = FontWeight.SemiBold,
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
fun CustomPackageCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
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
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Need a Custom Package?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Let us create a personalized package tailored to your specific requirements and budget",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            OutlinedButton(
                onClick = { /* Handle contact click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
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
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Contact Us for Custom Quote",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

 @Preview(showBackground = true)
 @Composable
fun PricingScreenPreview() {
    GouravActStudioTheme {
        PricingScreen()
    }
}