package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
        // Gradient orb 1 - Pink
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (100 + offsetX1).dp, y = (-100 + offsetY1).dp)
                .blur(120.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x45EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Gradient orb 2 - Purple
        Box(
            modifier = Modifier
                .size(450.dp)
                .offset(x = (-180 - offsetX1).dp, y = (400 - offsetY1).dp)
                .blur(130.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x408B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Gradient orb 3 - Orange
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = 150.dp, y = (600 + offsetY1).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x30F97316),
                            Color(0x00F97316)
                        )
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
                elevation = 20.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(32.dp),
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
                            Color(0xFF8B5CF6).copy(alpha = 0.06f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
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
                    Icon(
                        imageVector = Icons.Filled.Language,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Choose Your Language",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1A1A1A),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "अपनी भाषा चुनें",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

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
                        gradientColors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6))
                    )

                    LanguageButton(
                        modifier = Modifier.weight(1f),
                        language = "hi",
                        label = "हिन्दी",
                        flag = "🇮🇳",
                        isSelected = selectedLanguage == "hi",
                        onClick = { onLanguageSelected("hi") },
                        gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF06B6D4))
                    )
                }
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
    gradientColors: List<Color>
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else if (isSelected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Button Scale"
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .height(140.dp)
            .scale(scale)
            .shadow(
                elevation = if (isSelected) 16.dp else 8.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = gradientColors[0].copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White else Color(0xFFFAFAFA)
        ),
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isSelected) {
                        Modifier.background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    gradientColors[0].copy(alpha = 0.1f),
                                    Color.Transparent
                                )
                            )
                        )
                    } else Modifier
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Flag emoji
                Text(
                    text = flag,
                    fontSize = 40.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFF1A1A1A) else Color(0xFF666666)
                )

                if (isSelected) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(gradientColors)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionStatusCard(onGrantPermission: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color(0xFFF97316).copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF7ED)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Location Permission Required",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "We need location access to show you nearby studios and services",
                style = MaterialTheme.typography.bodySmall.copy(
                    lineHeight = 18.sp
                ),
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 0.95f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "Button Scale"
            )

            Button(
                onClick = onGrantPermission,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .scale(scale)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color(0xFFF97316).copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFF97316),
                                    Color(0xFFEC4899)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Grant Permission",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
