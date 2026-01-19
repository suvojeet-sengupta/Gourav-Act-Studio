package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.screens.gallery.PhotosScreen
import com.suvojeet.gauravactstudio.ui.screens.gallery.VideosScreen
import kotlinx.coroutines.delay

@Composable
fun GalleryScreen(navController: NavController, modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabItem("Photos", Icons.Filled.PhotoLibrary),
        TabItem("Videos", Icons.Filled.Videocam)
    )
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(20)
        isVisible = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFFFFBFE),
                        Color(0xFFF8F8FA)
                    )
                )
            )
    ) {
        // Decorative Background Orbs
        GalleryDecorativeBackground()

        Column(modifier = Modifier.fillMaxSize()) {
            // Modern Header
            GalleryHeader(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            // Content
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTabIndex) {
                    0 -> PhotosScreen(navController = navController)
                    1 -> VideosScreen(navController = navController)
                }
            }
        }
    }
}

data class TabItem(val title: String, val icon: ImageVector)

@Composable
private fun GalleryHeader(
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // Title Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gradient Icon
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFEC4899), Color(0xFF8B5CF6))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhotoLibrary,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(14.dp))
                
                Column {
                    Text(
                        text = stringResource(R.string.gallery_header_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        text = "Explore our portfolio",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF888888)
                    )
                }
            }

            // Modern Tab Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    ModernTab(
                        tab = tab,
                        isSelected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ModernTab(
    tab: TabItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFEC4899) else Color(0xFFF3F4F6),
        animationSpec = tween(300),
        label = "Tab Background"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color(0xFF6B7280),
        animationSpec = tween(300),
        label = "Tab Content"
    )
    
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "Tab Scale"
    )

    Surface(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = tab.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = tab.title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = contentColor
            )
        }
    }
}

@Composable
private fun GalleryDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Gallery BG")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb X"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Pink Orb - Top Right
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (150 + offsetX).dp, y = (100 + offsetY).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x30EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Purple Orb - Bottom Left
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-100 - offsetX).dp, y = (400 - offsetY).dp)
                .blur(110.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x258B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Subtle Cyan Orb
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (250 + offsetX / 2).dp, y = (550 + offsetY).dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x1A06B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )
    }
}