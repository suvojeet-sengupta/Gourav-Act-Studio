package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.components.AppLogo
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringResource
import com.suvojeet.gauravactstudio.R
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.blur
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(20)
        isVisible = true
    }

    val scrollState = rememberScrollState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.Home.route) {
            scrollState.scrollTo(0)
        }
    }

    Box(
        modifier = modifier
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
        // Enhanced decorative background
        LightDecorativeBackground(scrollState.value)

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Section with modern design
            HeroSection(isVisible, navController)

            Spacer(modifier = Modifier.height(48.dp))

            // Quick Stats with Modern Cards
            AnimatedContent(isVisible, delay = 160) {
                ModernQuickStatsSection()
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Features with modern design
AnimatedContent(isVisible, delay = 80) {
                ModernFeaturesSection(isVisible)
            }

            Spacer(modifier = Modifier.height(32.dp))

AnimatedContent(isVisible, delay = 240) {
                ModernAddressSection()
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun HeroSection(isVisible: Boolean, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(24.dp))

        // Main Photographer Image and Studio Name
        AnimatedContent(isVisible, delay = 20) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.gourav_photographer),
                    contentDescription = "Gaurav, Main Photographer",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Gaurav Act Digital Studio",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(isVisible, delay = 40) {
            Text(
                text = stringResource(R.string.home_hero_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 26.sp, // Changed font size to 26.sp
                    letterSpacing = 0.sp // Changed letter spacing to 0.sp
                ),
                fontWeight = FontWeight.Bold, // Changed to FontWeight.Bold
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center,

                // --- THE FIX ---
                // Force the Text to take the full width, allowing wrap
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Added horizontal padding

                maxLines = 2, // Allow wrapping to 2 lines
                softWrap = true // Enable soft wrapping
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(isVisible, delay = 200) {
            Text(
                text = stringResource(R.string.home_hero_subtitle),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        AnimatedContent(isVisible, delay = 120) {
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
            val animatedColor1 by animateColorAsState(
                targetValue = if (isPressed) Color(0xFF8B5CF6) else Color(0xFFEC4899),
                animationSpec = tween(durationMillis = 200),
                label = "Color1 Animation"
            )
            val animatedColor2 by animateColorAsState(
                targetValue = if (isPressed) Color(0xFFEC4899) else Color(0xFF8B5CF6),
                animationSpec = tween(durationMillis = 200),
                label = "Color2 Animation"
            )

            Button(
                onClick = {
                    navController.navigate(Screen.Services.route)
                },
                interactionSource = interactionSource,
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(0.85f)
                    .scale(scale)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = Color(0xFFEC4899).copy(alpha = 0.4f),
                        spotColor = Color(0xFF8B5CF6).copy(alpha = 0.4f)
                    ),
                shape = RoundedCornerShape(32.dp),
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
                                colors = listOf(animatedColor1, animatedColor2)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.home_explore_services),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LightDecorativeBackground(scrollOffset: Int = 0) {
    val infiniteTransition = rememberInfiniteTransition(label = "Background Animation")

    val offsetX1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset X"
    )

    val offsetY1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient orb 1 - Pink
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (120 + offsetX1).dp, y = (-80 + offsetY1 + scrollOffset * 0.1f).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x40EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Gradient orb 2 - Purple
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150 - offsetX1).dp, y = (350 - offsetY1 - scrollOffset * 0.05f).dp)
                .blur(120.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x358B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Gradient orb 3 - Cyan
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 80.dp, y = (650 + offsetY1 - scrollOffset * 0.08f).dp)
                .blur(110.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x3006B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )

        // Gradient orb 4 - Orange
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = 200.dp, y = (450 + offsetX1 + scrollOffset * 0.03f).dp)
                .blur(90.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x28F97316),
                            Color(0x00F97316)
                        )
                    )
                )
        )
    }
}

@Composable
fun ModernQuickStatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ModernStatCard(
            modifier = Modifier.weight(1f),
            number = "500+",
            label = stringResource(R.string.home_stat_happy_clients),
            icon = Icons.Filled.People,
            gradientColors = listOf(Color(0xFFEC4899), Color(0xFFF97316))
        )
        ModernStatCard(
            modifier = Modifier.weight(1f),
            number = "1000+",
            label = stringResource(R.string.home_stat_events_covered),
            icon = Icons.Filled.Event,
            gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6366F1))
        )
        ModernStatCard(
            modifier = Modifier.weight(1f),
            number = "5★",
            label = stringResource(R.string.home_stat_rated_service),
            icon = Icons.Filled.Star,
            gradientColors = listOf(Color(0xFF06B6D4), Color(0xFF0EA5E9))
        )
    }
}

@Composable
fun ModernStatCard(
    modifier: Modifier = Modifier,
    number: String,
    label: String,
    icon: ImageVector,
    gradientColors: List<Color>
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = gradientColors[0].copy(alpha = 0.25f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            gradientColors[0].copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(gradientColors)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = number,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun ModernFeaturesSection(isVisible: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(28.dp),
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
                            Color(0xFF8B5CF6).copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
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
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.home_features_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1A1A1A)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedContent(isVisible, delay = 600) {
                    ModernFeatureItem(
                        icon = Icons.Filled.HighQuality,
                        title = stringResource(R.string.home_feature_quality_title),
                        description = stringResource(R.string.home_feature_quality_description),
                        accentColor = Color(0xFFEC4899)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(isVisible, delay = 280) {
                    ModernFeatureItem(
                        icon = Icons.Filled.Speed,
                        title = stringResource(R.string.home_feature_delivery_title),
                        description = stringResource(R.string.home_feature_delivery_description),
                        accentColor = Color(0xFF8B5CF6)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(isVisible, delay = 320) {
                    ModernFeatureItem(
                        icon = Icons.Filled.PriceCheck,
                        title = stringResource(R.string.home_feature_packages_title),
                        description = stringResource(R.string.home_feature_packages_description),
                        accentColor = Color(0xFF06B6D4)
                    )
                }
            }
        }
    }
}

@Composable
fun ModernFeatureItem(
    icon: ImageVector,
    title: String,
    description: String,
    accentColor: Color
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(accentColor.copy(alpha = 0.12f))
                .padding(2.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 20.sp
                ),
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
fun ModernAddressSection() {
    val context = LocalContext.current
    val mapUrl = "https://maps.app.goo.gl/Wg2P5A4AafHxZsJF6"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(28.dp),
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
                            Color(0xFFEC4899).copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFEC4899),
                                    Color(0xFFF97316)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Our Studio Location",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Village nagla dhimar, Etah road near bhondela politecnic college, tundla firozabad (up), Pin code 283204",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                val animatedColor1 by animateColorAsState(
                    targetValue = if (isPressed) Color(0xFFF97316) else Color(0xFFEC4899),
                    animationSpec = tween(durationMillis = 200),
                    label = "Color1 Animation"
                )
                val animatedColor2 by animateColorAsState(
                    targetValue = if (isPressed) Color(0xFFEC4899) else Color(0xFFF97316),
                    animationSpec = tween(durationMillis = 200),
                    label = "Color2 Animation"
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                        context.startActivity(intent)
                    },
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .scale(scale)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(28.dp),
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
                                    colors = listOf(animatedColor1, animatedColor2)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "View on Google Maps",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GauravActStudioTheme {
        HomeScreen(navController = rememberNavController())
    }
}