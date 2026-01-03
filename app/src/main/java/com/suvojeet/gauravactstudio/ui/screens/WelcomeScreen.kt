package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.components.AppLogo
import com.suvojeet.gauravactstudio.util.Prefs
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(onCompletion: () -> Unit) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var isVisible by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    LaunchedEffect(permissionsState.allPermissionsGranted, selectedLanguage) {
        if (permissionsState.allPermissionsGranted && selectedLanguage != null) {
            delay(300)
            onCompletion()
        }
    }

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
        // Animated decorative background
        WelcomeDecorativeBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Hero Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Logo with glow effect
                AnimatedContent(isVisible) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 24.dp,
                                shape = CircleShape,
                                ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f),
                                spotColor = Color(0xFF8B5CF6).copy(alpha = 0.3f)
                            )
                    ) {
                        AppLogo()
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Welcome Text
                AnimatedContent(isVisible, delay = 80) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Welcome to",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp
                            ),
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gaurav Act Digital Studio",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 28.sp,
                                letterSpacing = 0.sp
                            ),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A1A1A),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                AnimatedContent(isVisible, delay = 160) {
                    Text(
                        text = "Capturing your precious moments with creativity and passion",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        ),
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Language Selection Card
                AnimatedContent(isVisible, delay = 240) {
                    LanguageSelectionCard(
                        selectedLanguage = selectedLanguage,
                        onLanguageSelected = { language ->
                            selectedLanguage = language
                            Prefs.setLanguage(context, language)
                        }
                    )
                }
            }

            // Bottom section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Permission status
                AnimatedContent(isVisible, delay = 320) {
                    if (selectedLanguage != null && !permissionsState.allPermissionsGranted) {
                        PermissionStatusCard(
                            onGrantPermission = {
                                permissionsState.launchMultiplePermissionRequest()
                            }
                        )
                    } else if (selectedLanguage != null && permissionsState.allPermissionsGranted) {
                        // Show continue button if language selected and permissions granted
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = onCompletion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B5CF6)
                            )
                        ) {
                            Text("Continue", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Skip button (if needed)
                if (!permissionsState.allPermissionsGranted && selectedLanguage != null) {
                    AnimatedContent(isVisible, delay = 400) {
                        TextButton(
                            onClick = onCompletion
                        ) {
                            Text(
                                text = "Skip for now",
                                color = Color(0xFF666666),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Background Animation")

    val offsetX1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset X"
    )

    val offsetY1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient orb 1 - Pink (Top Left)
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-50 + offsetX1).dp, y = (-100 + offsetY1).dp)
                .blur(100.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFEC4899).copy(alpha = 0.2f), Color.Transparent)
                    )
                )
        )

        // Gradient orb 2 - Purple (Bottom Right)
        Box(
            modifier = Modifier
                .size(450.dp)
                .offset(x = 100.dp, y = 400.dp)
                .blur(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF8B5CF6).copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )
    }
}

@Composable
fun LanguageSelectionCard(
    selectedLanguage: String?,
    onLanguageSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp, // High elevation for floating effect
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Choose Language",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "भाषा चुनें",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Language Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LanguageButton(
                    modifier = Modifier.weight(1f),
                    language = "en",
                    label = "English",
                    flag = "🇬🇧",
                    isSelected = selectedLanguage == "en",
                    onClick = { onLanguageSelected("en") },
                    primaryColor = Color(0xFFEC4899)
                )

                LanguageButton(
                    modifier = Modifier.weight(1f),
                    language = "hi",
                    label = "हिन्दी",
                    flag = "🇮🇳",
                    isSelected = selectedLanguage == "hi",
                    onClick = { onLanguageSelected("hi") },
                    primaryColor = Color(0xFF8B5CF6)
                )
            }
        }
    }
}

@Composable
fun LanguageButton(
    modifier: Modifier = Modifier,
    language: String,
    label: String,
    flag: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    primaryColor: Color
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) primaryColor else Color.Transparent,
        label = "border"
    )
    
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) primaryColor.copy(alpha = 0.05f) else Color(0xFFF9FAFB),
        label = "container"
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = flag, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) primaryColor else Color(0xFF4B5563)
            )
        }
    }
}

@Composable
fun PermissionStatusCard(onGrantPermission: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp, 
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = Color(0xFFEC4899),
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Enable Location",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "To show you the best studios and services near you.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onGrantPermission,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEC4899)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = "Allow Access", 
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
