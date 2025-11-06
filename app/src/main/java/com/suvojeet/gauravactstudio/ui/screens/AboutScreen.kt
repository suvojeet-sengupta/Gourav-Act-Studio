package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.ui.graphics.vector.path
import androidx.compose.material.icons.materialIcon

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.components.FeatureItem
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import kotlinx.coroutines.delay




val Icons.Filled.Instagram: ImageVector
    get() {
        if (_instagram != null) {
            return _instagram!!
        }
        _instagram = materialIcon(name = "Filled.Instagram") {
            path {
                moveTo(7.8f, 2.0f)
                horizontalLineToRelative(8.4f)
                curveToRelative(2.9f, 0.0f, 5.2f, 2.3f, 5.2f, 5.2f)
                verticalLineToRelative(8.4f)
                curveToRelative(0.0f, 2.9f, -2.3f, 5.2f, -5.2f, 5.2f)
                horizontalLineTo(7.8f)
                curveTo(4.9f, 22.0f, 2.6f, 19.7f, 2.6f, 16.8f)
                verticalLineTo(7.2f)
                curveTo(2.6f, 4.3f, 4.9f, 2.0f, 7.8f, 2.0f)
                close()
                moveTo(12.0f, 7.0f)
                curveToRelative(-2.8f, 0.0f, -5.0f, 2.2f, -5.0f, 5.0f)
                reflectiveCurveToRelative(2.2f, 5.0f, 5.0f, 5.0f)
                reflectiveCurveToRelative(5.0f, -2.2f, 5.0f, -5.0f)
                reflectiveCurveToRelative(-2.2f, -5.0f, -5.0f, -5.0f)
                close()
                moveTo(12.0f, 15.0f)
                curveToRelative(-1.7f, 0.0f, -3.0f, -1.3f, -3.0f, -3.0f)
                reflectiveCurveToRelative(1.3f, -3.0f, 3.0f, -3.0f)
                reflectiveCurveToRelative(3.0f, 1.3f, 3.0f, 3.0f)
                reflectiveCurveToRelative(-1.3f, 3.0f, -3.0f, 3.0f)
                close()
                moveTo(16.8f, 6.2f)
                curveToRelative(-0.6f, 0.0f, -1.0f, 0.4f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.4f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.4f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.4f, -1.0f, -1.0f, -1.0f)
                close()
            }
        }
        return _instagram!!
    }
private var _instagram: ImageVector? = null

val Icons.Filled.YouTube: ImageVector
    get() {
        if (_youTube != null) {
            return _youTube!!
        }
        _youTube = materialIcon(name = "Filled.YouTube") {
            path {
                moveTo(21.58f, 7.19f)
                curveTo(21.35f, 6.4f, 20.8f, 5.85f, 20.01f, 5.62f)
                curveTo(18.2f, 5.0f, 12.0f, 5.0f, 12.0f, 5.0f)
                reflectiveCurveTo(5.8f, 5.0f, 3.99f, 5.62f)
                curveTo(3.2f, 5.85f, 2.65f, 6.4f, 2.42f, 7.19f)
                curveTo(1.8f, 9.0f, 1.8f, 12.0f, 1.8f, 12.0f)
                reflectiveCurveToRelative(0.0f, 3.0f, 0.62f, 4.81f)
                curveToRelative(0.23f, 0.79f, 0.78f, 1.34f, 1.57f, 1.57f)
                curveTo(5.8f, 19.0f, 12.0f, 19.0f, 12.0f, 19.0f)
                reflectiveCurveToRelative(6.2f, 0.0f, 8.01f, -0.62f)
                curveToRelative(0.79f, -0.23f, 1.34f, -0.78f, 1.57f, -1.57f)
                curveTo(22.2f, 15.0f, 22.2f, 12.0f, 22.2f, 12.0f)
                reflectiveCurveToRelative(0.0f, -3.0f, -0.62f, -4.81f)
                close()
                moveTo(10.0f, 15.0f)
                verticalLineTo(9.0f)
                lineToRelative(5.2f, 3.0f)
                lineTo(10.0f, 15.0f)
                close()
            }
        }
        return _youTube!!
    }
private var _youTube: ImageVector? = null

@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Header Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(isVisible) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedContent(isVisible, delay = 200) {
                        Text(
                            text = "Gourav Act Studio",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedContent(isVisible, delay = 400) {
                        Text(
                            text = "Professional Photography & Videography",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                // About Section
                AnimatedContent(isVisible, delay = 600) {
                    AboutCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Stats
                AnimatedContent(isVisible, delay = 800) {
                    QuickStatsCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contact Information
                AnimatedContent(isVisible, delay = 1000) {
                    ContactCard(
                        onEmailClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:gauravkumarpjt@gmail.com")
                            }
                            context.startActivity(intent)
                        },
                        onPhoneClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:+919354654066")
                            }
                            context.startActivity(intent)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Features Section
                AnimatedContent(isVisible, delay = 1200) {
                    FeaturesCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Social Media
                AnimatedContent(isVisible, delay = 1400) {
                    SocialMediaCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Settings Button
                AnimatedContent(isVisible, delay = 1600) {
                    SettingsButton(navController)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}



@Composable
fun AboutCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "About Us",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We are a passionate team of photographers and videographers dedicated to capturing life's most precious moments. With years of experience and state-of-the-art equipment, we transform your special occasions into timeless memories.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Our mission is to deliver exceptional quality and creative artistry that exceeds expectations. Every project is unique, and we approach each one with fresh eyes and innovative ideas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
fun QuickStatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Our Achievements",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Filled.People,
                    number = "500+",
                    label = "Happy Clients",
                    gradient = listOf(Color(0xFFEC4899), Color(0xFFF97316))
                )
                StatItem(
                    icon = Icons.Filled.Event,
                    number = "1000+",
                    label = "Events Covered",
                    gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
                )
                StatItem(
                    icon = Icons.Filled.Star,
                    number = "5.0",
                    label = "Rating",
                    gradient = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
                )
            }
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, number: String, label: String, gradient: List<Color>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(brush = Brush.linearGradient(gradient)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = number,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ContactCard(onEmailClick: () -> Unit, onPhoneClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Get In Touch",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ContactItem(
                icon = Icons.Filled.Email,
                title = "Email",
                info = "gauravkumarpjt@gmail.com",
                gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
                onClick = onEmailClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactItem(
                icon = Icons.Filled.Phone,
                title = "Phone",
                info = "+91 93546 54066",
                gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
                onClick = onPhoneClick
            )
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    title: String,
    info: String,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF9FAFB)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(brush = Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun FeaturesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Why Choose Us",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FeatureItem(
                icon = Icons.Filled.HighQuality,
                title = "Premium Quality",
                description = "4K videography and high-resolution photography"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureItem(
                icon = Icons.Filled.Speed,
                title = "Fast Delivery",
                description = "Quick turnaround time for your memories"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureItem(
                icon = Icons.Filled.PriceCheck,
                title = "Affordable Pricing",
                description = "Flexible packages to suit every budget"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureItem(
                icon = Icons.Filled.Support,
                title = "24/7 Support",
                description = "Always available for your queries"
            )
        }
    }
}



@Composable
fun SocialMediaCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Follow Us",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialButton(
                    icon = Icons.Filled.Facebook,
                    label = "Facebook",
                    gradient = listOf(Color(0xFF1877F2), Color(0xFF42B0FF))
                )
                SocialButton(
                    icon = Icons.Filled.Instagram,
                    label = "Instagram",
                    gradient = listOf(Color(0xFFE4405F), Color(0xFFFCAF45))
                )
                SocialButton(
                    icon = Icons.Filled.YouTube,
                    label = "YouTube",
                    gradient = listOf(Color(0xFFFF0000), Color(0xFFFF6B6B))
                )
            }
        }
    }
}

@Composable
fun SocialButton(icon: ImageVector, label: String, gradient: List<Color>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle click */ }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(brush = Brush.linearGradient(gradient)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun SettingsButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.Settings.route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Settings & Preferences",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
