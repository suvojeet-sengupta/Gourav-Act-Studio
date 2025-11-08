package com.suvojeet.gauravactstudio.ui.screens

import coil.compose.AsyncImage
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.SolidColor

import androidx.compose.ui.graphics.vector.path
import androidx.compose.material.icons.materialIcon

import android.content.Intent
import android.content.Context
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
            path(fill = SolidColor(Color.Black)) {
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
            path(fill = SolidColor(Color.Black)) {
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

val Icons.Filled.WhatsApp: ImageVector
    get() {
        if (_whatsApp != null) {
            return _whatsApp!!
        }
        _whatsApp = materialIcon(name = "Filled.WhatsApp") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                curveToRelative(0.0f, 1.75f, 0.45f, 3.4f, 1.26f, 4.86f)
                lineTo(2.0f, 22.0f)
                lineToRelative(5.26f, -1.24f)
                curveTo(8.6f, 21.55f, 10.25f, 22.0f, 12.0f, 22.0f)
                curveToRelative(5.52f, 0.0f, 10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(17.0f, 15.88f)
                curveToRelative(-0.25f, 0.13f, -1.47f, 0.72f, -1.7f, 0.8f)
                curveToRelative(-0.23f, 0.08f, -0.39f, 0.13f, -0.56f, 0.3f)
                curveToRelative(-0.17f, 0.17f, -0.68f, 0.85f, -0.83f, 1.02f)
                curveToRelative(-0.15f, 0.17f, -0.3f, 0.18f, -0.56f, 0.06f)
                curveToRelative(-0.25f, -0.12f, -1.07f, -0.39f, -2.04f, -1.26f)
                curveToRelative(-0.76f, -0.66f, -1.27f, -1.47f, -1.42f, -1.72f)
                curveToRelative(-0.15f, -0.25f, -0.02f, -0.38f, 0.1f, -0.5f)
                curveToRelative(0.11f, -0.11f, 0.25f, -0.28f, 0.38f, -0.42f)
                curveToRelative(0.12f, -0.14f, 0.17f, -0.25f, 0.25f, -0.41f)
                curveToRelative(0.08f, -0.17f, 0.04f, -0.31f, -0.02f, -0.43f)
                curveToRelative(-0.06f, -0.12f, -0.56f, -1.34f, -0.76f, -1.84f)
                curveToRelative(-0.2f, -0.48f, -0.41f, -0.42f, -0.56f, -0.42f)
                curveToRelative(-0.14f, 0.0f, -0.3f, 0.0f, -0.47f, 0.0f)
                curveToRelative(-0.17f, 0.0f, -0.44f, 0.06f, -0.68f, 0.31f)
                curveToRelative(-0.24f, 0.25f, -0.93f, 0.9f, -0.93f, 2.2f)
                curveToRelative(0.0f, 1.3f, 0.95f, 2.55f, 1.08f, 2.72f)
                curveToRelative(0.13f, 0.17f, 1.87f, 2.93f, 4.53f, 4.0f)
                curveToRelative(0.64f, 0.26f, 1.14f, 0.41f, 1.54f, 0.52f)
                curveToRelative(0.6f, 0.17f, 1.15f, 0.14f, 1.58f, 0.09f)
                curveToRelative(0.48f, -0.06f, 1.47f, -0.6f, 1.67f, -1.18f)
                curveToRelative(0.2f, -0.58f, 0.2f, -1.08f, 0.14f, -1.18f)
                curveToRelative(-0.06f, -0.1f, -0.22f, -0.16f, -0.47f, -0.28f)
                close()
            }
        }
        return _whatsApp!!
    }
private var _whatsApp: ImageVector? = null

@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(40)
        isVisible = true
    }

    Box(
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Header Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var showFullScreenImage by remember { mutableStateOf(false) }

                    AsyncImage(
                        model = R.drawable.studioposter,
                        contentDescription = "Gaurav Act Studio Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                            .clickable { showFullScreenImage = true },
                        contentScale = ContentScale.Crop
                    )

                    if (showFullScreenImage) {
                        FullScreenImageDialog(
                            imageResId = R.drawable.studioposter,
                            onDismiss = { showFullScreenImage = false }
                        )
                    }

                    AnimatedContent(isVisible, delay = 40) {
                        Text(
                            text = "Gaurav Act Studio",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedContent(isVisible, delay = 80) {
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
                AnimatedContent(isVisible, delay = 120) {
                    AboutCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Stats
                AnimatedContent(isVisible, delay = 160) {
                    QuickStatsCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contact Information
                AnimatedContent(isVisible, delay = 200) {
                    ContactCard(
                        context = context,
                        onEmailClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:gauravkumarpjt@gmail.com")
                            }
                            context.startActivity(intent)
                        },
                        phoneNumbers = listOf("+91 93546 54066", "+91 70179 72737")
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Features Section
                AnimatedContent(isVisible, delay = 240) {
                    FeaturesCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Social Media
                AnimatedContent(isVisible, delay = 280) {
                    SocialMediaCard()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Settings Button
                AnimatedContent(isVisible, delay = 320) {
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
fun ContactCard(context: Context, onEmailClick: () -> Unit, phoneNumbers: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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

            phoneNumbers.forEach { phoneNumber ->
                ContactItem(
                    icon = Icons.Filled.Phone,
                    title = "Phone",
                    info = phoneNumber,
                    gradient = listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${phoneNumber.replace(" ", "")}")
                        }
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            ContactItem(
                icon = Icons.Filled.WhatsApp,
                title = "WhatsApp",
                info = "+91 93546 54066",
                gradient = listOf(Color(0xFF25D366), Color(0xFF128C7E)),
                onClick = {
                    val phoneNumber = "+919354654066"
                    val message = "I get your contact information from your app. I have some query."
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
                    }
                    context.startActivity(intent)
                }
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
        color = Color(0xFFFAFAFA)
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    gradient = listOf(Color(0xFF1877F2), Color(0xFF42B0FF)),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/17e6BCSVKr/"))
                        context.startActivity(intent)
                    }
                )
                SocialButton(
                    icon = Icons.Filled.Instagram,
                    label = "Instagram",
                    gradient = listOf(Color(0xFFE4405F), Color(0xFFFCAF45)),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/invites/contact/?utm_source=ig_contact_invite&utm_medium=copy_link&utm_content=xz8exwz"))
                        context.startActivity(intent)
                    }
                )
                SocialButton(
                    icon = Icons.Filled.YouTube,
                    label = "YouTube",
                    gradient = listOf(Color(0xFFFF0000), Color(0xFFFF6B6B)),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@gauravact?si=_RPRL0fM5-UZr5YN"))
                        context.startActivity(intent)
                    }
                )
                SocialButton(
                    icon = Icons.Filled.WhatsApp,
                    label = "WhatsApp",
                    gradient = listOf(Color(0xFF25D366), Color(0xFF128C7E)),
                    onClick = {
                        val phoneNumber = "+919354654066"
                        val message = "I get your contact information from your app. I have some query."
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun SocialButton(icon: ImageVector, label: String, gradient: List<Color>, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
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

@Composable
fun FullScreenImageDialog(imageResId: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(alpha = 0.8f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageResId,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
