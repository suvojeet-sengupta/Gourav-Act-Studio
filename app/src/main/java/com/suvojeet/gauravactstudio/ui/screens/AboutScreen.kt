package com.suvojeet.gauravactstudio.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import kotlinx.coroutines.delay

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
            .background(Color(0xFFF4F5F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Area
            Surface(
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.about_header_title),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F2937)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE5E7EB)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = R.drawable.gourav_photographer,
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Gaurav Kumar",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                    Text(
                        text = stringResource(R.string.professional_photography_videography),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Section: Studio Info
                SectionTitle(stringResource(R.string.about_section_info))
                AboutActionCard(
                    icon = Icons.Outlined.Info,
                    title = stringResource(R.string.about_us_title),
                    subtitle = stringResource(R.string.about_studio_description),
                    onClick = {}
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                AboutActionCard(
                    icon = Icons.Outlined.Verified,
                    title = "Achievements",
                    subtitle = "500+ Happy Clients, 1000+ Events covered",
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Section: Contact & Support
                SectionTitle(stringResource(R.string.about_section_contact))
                AboutActionCard(
                    icon = Icons.Outlined.Email,
                    title = "Email Us",
                    subtitle = "gauravkumarpjt@gmail.com",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:gauravkumarpjt@gmail.com")
                        }
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                AboutActionCard(
                    icon = Icons.Outlined.Phone,
                    title = "Call Us",
                    subtitle = "+91 93546 54066",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:+919354654066")
                        }
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                AboutActionCard(
                    icon = Icons.Outlined.Chat,
                    title = "WhatsApp",
                    subtitle = "Chat with us instantly",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?phone=+919354654066")
                        }
                        context.startActivity(intent)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Section: Social Media
                SectionTitle("Follow Us")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SocialIconBox(Icons.Filled.Facebook, Color(0xFF1877F2)) {
                         context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/17e6BCSVKr/")))
                    }
                    SocialIconBox(Icons.Filled.CameraAlt, Color(0xFFE4405F)) { // Simplified Instagram
                         context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/invites/contact/")))
                    }
                    SocialIconBox(Icons.Filled.PlayArrow, Color(0xFFFF0000)) { // YouTube
                         context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@gauravact")))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App Settings
                Button(
                    onClick = { navController.navigate(Screen.Settings.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2937))
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("App Settings", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF6B7280),
        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
    )
}

@Composable
fun AboutActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF3F4F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1F2937),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFD1D5DB)
            )
        }
    }
}

@Composable
fun SocialIconBox(icon: ImageVector, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
    }
}