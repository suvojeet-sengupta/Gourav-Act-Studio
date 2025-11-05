package com.suvojeet.gouravactstudio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvojeet.gouravactstudio.ui.theme.GouravActStudioTheme
import kotlinx.coroutines.delay
import com.suvojeet.gouravactstudio.ui.components.AnimatedContent

 @Composable
fun ContactScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContactMail,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    AnimatedContent(isVisible, delay = 200) {
                        Text(
                            text = "Get In Touch",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    AnimatedContent(isVisible, delay = 400) {
                        Text(
                            text = "We'd love to hear from you! Reach out for bookings or inquiries",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                // Contact Info Cards
                AnimatedContent(isVisible, delay = 600) {
                    ContactInfoCard(
                        icon = Icons.Filled.Call,
                        title = "Call Us",
                        subtitle = "Available 24/7 for your queries",
                        info = "+91 93546 54066",
                        secondaryInfo = "+91 70179 72737",
                        gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:+919354654066")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(isVisible, delay = 800) {
                    ContactInfoCard(
                        icon = Icons.Filled.Email,
                        title = "Email Us",
                        subtitle = "Get a response within 24 hours",
                        info = "gauravkumarpjt @gmail.com",
                        gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:gauravkumarpjt @gmail.com")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(isVisible, delay = 1000) {
                    ContactInfoCard(
                        icon = Icons.Filled.LocationOn,
                        title = "Visit Our Studio",
                        subtitle = "Come see our portfolio in person",
                        info = "Village Nagla Dhimar, Etah Road",
                        secondaryInfo = "Near Bhondela Polytechnic College",
                        tertiaryInfo = "Tundla, Firozabad (UP) - 283204",
                        gradient = listOf(Color(0xFFEC4899), Color(0xFFF97316)),
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("geo:0,0?q=Village+Nagla+Dhimar,+Tundla,+Firozabad,+UP")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Social Media Section
                AnimatedContent(isVisible, delay = 1200) {
                    SocialMediaSection()
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Business Hours Card
                AnimatedContent(isVisible, delay = 1400) {
                    BusinessHoursCard()
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Map Placeholder with CTA
                AnimatedContent(isVisible, delay = 1600) {
                    MapCard(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("geo:0,0?q=Village+Nagla+Dhimar,+Tundla,+Firozabad,+UP")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

 @Composable
fun ContactInfoCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    info: String,
    secondaryInfo: String? = null,
    tertiaryInfo: String? = null,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gradient Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(brush = Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                if (secondaryInfo != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = secondaryInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                if (tertiaryInfo != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tertiaryInfo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

 @Composable
fun SocialMediaSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Follow Us On Social Media",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialMediaButton(
                    icon = Icons.Filled.Instagram,
                    label = "Instagram",
                    gradient = listOf(Color(0xFFE4405F), Color(0xFFFCAF45))
                )
                SocialMediaButton(
                    icon = Icons.Filled.Facebook,
                    label = "Facebook",
                    gradient = listOf(Color(0xFF1877F2), Color(0xFF42B0FF))
                )
                SocialMediaButton(
                    icon = Icons.Filled.YouTube,
                    label = "YouTube",
                    gradient = listOf(Color(0xFFFF0000), Color(0xFFFF6B6B))
                )
            }
        }
    }
}

 @Composable
fun SocialMediaButton(icon: ImageVector, label: String, gradient: List<Color>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle social media click */ }