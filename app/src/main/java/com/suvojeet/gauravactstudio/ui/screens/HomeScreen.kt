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
import androidx.compose.material.icons.outlined.Search
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
import com.suvojeet.gauravactstudio.ui.components.LightDecorativeBackground
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.res.stringResource
import com.suvojeet.gauravactstudio.R
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.blur
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.Home.route) {
            delay(20) // Keep a small delay for animation
            isVisible = true
            scrollState.scrollTo(0) // Also scroll to top when Home is active
        } else {
            isVisible = false // Reset when navigating away
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F5F9)) // Slightly grey background like apps
    ) {
        // LightDecorativeBackground(scrollState.value) // Optional: Keep or remove for cleaner look

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            // 1. Top Bar & Search Section
            TopHeaderSection()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            SearchSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Categories ("What's on your mind?")
            AnimatedContent(isVisible, delay = 50) {
                CategorySection(navController)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Hero / Banners
            AnimatedContent(isVisible, delay = 100) {
                PromoBannerSection(navController)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Quick Stats
            AnimatedContent(isVisible, delay = 150) {
                 Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                     Text(
                        text = stringResource(R.string.home_studio_highlights),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    ModernQuickStatsSection()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Your Photos Section (New)
            AnimatedContent(isVisible, delay = 200) {
                YourPhotosSection(navController)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 6. Popular Services (Horizontal Scroll)
            AnimatedContent(isVisible, delay = 250) {
                PopularServicesSection(navController)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 7. Address / Footer
            AnimatedContent(isVisible, delay = 300) {
                 Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ModernAddressSection()
                 }
            }

            Spacer(modifier = Modifier.height(100.dp)) // Bottom padding for navigation bar
        }
    }
}

@Composable
fun YourPhotosSection(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { navController.navigate(Screen.YourPhotos.route) }
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF06B6D4), Color(0xFF3B82F6))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CloudDownload,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.your_photos_screen),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.home_feature_your_photos_description),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun TopHeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location",
            tint = Color(0xFFEC4899),
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Gaurav Act Studio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xFF1F2937)
                )
            }
            Text(
                text = stringResource(R.string.home_location_label),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        
        // Profile or Menu Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE5E7EB)),
            contentAlignment = Alignment.Center
        ) {
             Image(
                painter = painterResource(id = R.drawable.gourav_photographer),
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SearchSection() {
    var text by remember { mutableStateOf("") }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color(0xFFEC4899)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.home_search_placeholder),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9CA3AF)
            )
             Spacer(modifier = Modifier.weight(1f))
             Icon(
                imageVector = Icons.Filled.Mic,
                contentDescription = "Voice Search",
                tint = Color(0xFFEC4899)
            )
        }
    }
}

@Composable
fun CategorySection(navController: NavController) {
    Column {
        Text(
            text = stringResource(R.string.home_category_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CategoryItem("Wedding", Icons.Filled.Favorite, Color(0xFFFFE4E6), Color(0xFFEC4899))
            CategoryItem("Events", Icons.Filled.Event, Color(0xFFE0E7FF), Color(0xFF6366F1))
            CategoryItem("Portraits", Icons.Filled.Face, Color(0xFFDCFCE7), Color(0xFF10B981))
            CategoryItem("Videos", Icons.Filled.Videocam, Color(0xFFFEF3C7), Color(0xFFF59E0B))
            CategoryItem("Pre-Wed", Icons.Filled.FavoriteBorder, Color(0xFFF3E8FF), Color(0xFFA855F7))
        }
    }
}

@Composable
fun CategoryItem(
    name: String, 
    icon: ImageVector, 
    bgColor: Color, 
    iconColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(bgColor)
                .clickable { /* Handle click */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PromoBannerSection(navController: NavController) {
    // A simplified horizontal scroll for banners
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner 1
        PromoBannerCard(
            title = stringResource(R.string.home_banner_1_title),
            subtitle = stringResource(R.string.home_banner_1_subtitle),
            gradientColors = listOf(Color(0xFFEC4899), Color(0xFFF43F5E)),
            onClick = { navController.navigate(Screen.Services.route) }
        )
        
        // Banner 2
        PromoBannerCard(
            title = stringResource(R.string.home_banner_2_title),
            subtitle = stringResource(R.string.home_banner_2_subtitle),
            gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6366F1)),
             onClick = { navController.navigate(Screen.Services.route) }
        )
    }
}

@Composable
fun PromoBannerCard(
    title: String,
    subtitle: String,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(gradientColors))
        ) {
            // Decorative circles
            Box(
                modifier = Modifier
                    .offset(x = 200.dp, y = (-20).dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            )
            Box(
                modifier = Modifier
                    .offset(x = 240.dp, y = 80.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_book_now),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = gradientColors[0]
                    )
                }
            }
        }
    }
}

@Composable
fun PopularServicesSection(navController: NavController) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.home_popular_services_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
            TextButton(onClick = { navController.navigate(Screen.Services.route) }) {
                Text(
                    text = stringResource(R.string.home_see_all),
                    color = Color(0xFFEC4899),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal List of Vertical Cards
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PopularServiceCard(
                title = stringResource(R.string.home_popular_service_1_title),
                price = stringResource(R.string.home_popular_service_1_price),
                rating = "4.8",
                imageColor = Color(0xFFFFE4E6),
                icon = Icons.Filled.CameraAlt
            )
            PopularServiceCard(
                title = stringResource(R.string.home_popular_service_2_title),
                price = stringResource(R.string.home_popular_service_2_price),
                rating = "4.9",
                imageColor = Color(0xFFE0E7FF),
                icon = Icons.Filled.Videocam
            )
             PopularServiceCard(
                title = stringResource(R.string.home_popular_service_3_title),
                price = stringResource(R.string.home_popular_service_3_price),
                rating = "5.0",
                imageColor = Color(0xFFDCFCE7),
                icon = Icons.Filled.Flight
            )
        }
    }
}

@Composable
fun PopularServiceCard(
    title: String,
    price: String,
    rating: String,
    imageColor: Color,
    icon: ImageVector
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Placeholder Image Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(imageColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black.copy(alpha = 0.1f),
                    modifier = Modifier.size(48.dp)
                )
            }
            
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = price,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF22C55E))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
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
                elevation = 4.dp, // Reduced elevation for cleaner look
                shape = RoundedCornerShape(16.dp), // Reduced corner radius
                ambientColor = gradientColors[0].copy(alpha = 0.25f)
            ),
        shape = RoundedCornerShape(16.dp),
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
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
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
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun ModernAddressSection() {
    val context = LocalContext.current
    val mapUrl = "https://maps.app.goo.gl/Wg2P5A4AafHxZsJF6"
    val reviewUrl = "https://search.google.com/local/writereview?placeid=ChIJIfpBPgBndDkR4uZfZxPF5iQ"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(20.dp),
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
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                     Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEC4899).copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFEC4899),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.home_visit_studio_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Shop at Nagla Beech, Opposite Police Chowki",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1F2937)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Village nagla dhimar, Etah road near bhondela politecnic college, tundla firozabad (up), Pin code 283204",
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC4899))
                    ) {
                        Text(stringResource(R.string.home_direction), fontSize = 14.sp)
                    }
                    
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFF06B6D4))
                    ) {
                        Text(stringResource(R.string.home_rate_us), color = Color(0xFF06B6D4), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}