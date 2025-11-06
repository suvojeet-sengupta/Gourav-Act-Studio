package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.components.AppLogo
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringResource
import com.suvojeet.gauravactstudio.R

 @Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
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
                        Color(0xFFE9ECEF),
                        Color(0xFFDEE2E6)
                    )
                )
            )
    ) {
        // Decorative background circles
        DecorativeBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Hero Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedContent(isVisible) {
                    AppLogo()
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                AnimatedContent(isVisible, delay = 200) {
                    Text(
                        text = stringResource(R.string.home_hero_title),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                AnimatedContent(isVisible, delay = 400) {
                    Text(
                        text = stringResource(R.string.home_hero_subtitle),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                AnimatedContent(isVisible, delay = 600) {
                    Button(
                        onClick = { navController.navigate(Screen.Services.route) },
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(0.8f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.home_explore_services),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Quick Stats Section
            AnimatedContent(isVisible, delay = 800) {
                QuickStatsSection()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Features Section
            AnimatedContent(isVisible, delay = 1000) {
                FeaturesSection()
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}



 @Composable
fun DecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    
    val scale1 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    val scale2 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Top right circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scale1)
                .offset(x = 150.dp, y = (-50).dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x40EC4899),
                            Color(0x10EC4899)
                        )
                    )
                )
        )
        
        // Bottom left circle
        Box(
            modifier = Modifier
                .size(250.dp)
                .scale(scale2)
                .offset(x = (-100).dp, y = 500.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x408B5CF6),
                            Color(0x108B5CF6)
                        )
                    )
                )
        )
    }
}

 @Composable
fun QuickStatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(number = "500+", label = stringResource(R.string.home_stat_happy_clients), icon = Icons.Filled.People)
        StatItem(number = "1000+", label = stringResource(R.string.home_stat_events_covered), icon = Icons.Filled.Event)
        StatItem(number = "5★", label = stringResource(R.string.home_stat_rated_service), icon = Icons.Filled.Star)
    }
}

 @Composable
fun StatItem(number: String, label: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = number,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

 @Composable
fun FeaturesSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.home_features_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            FeatureItem(
                icon = Icons.Filled.HighQuality,
                title = stringResource(R.string.home_feature_quality_title),
                description = stringResource(R.string.home_feature_quality_description)
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            FeatureItem(
                icon = Icons.Filled.Speed,
                title = stringResource(R.string.home_feature_delivery_title),
                description = stringResource(R.string.home_feature_delivery_description)
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            FeatureItem(
                icon = Icons.Filled.PriceCheck,
                title = stringResource(R.string.home_feature_packages_title),
                description = stringResource(R.string.home_feature_packages_description)
            )
        }
    }
}

 @Composable
fun FeatureItem(icon: ImageVector, title: String, description: String) {
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
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
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