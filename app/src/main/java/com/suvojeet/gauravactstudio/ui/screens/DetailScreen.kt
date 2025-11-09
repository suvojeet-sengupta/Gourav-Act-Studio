package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import com.suvojeet.gauravactstudio.ui.components.VideoPlayer
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    mediaUrl: String,
    mediaType: String
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(50)
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF2D2D2D),
                        Color(0xFF1A1A1A)
                    )
                )
            )
    ) {
        // Enhanced decorative background
        DetailDecorativeBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                ModernTopAppBar(
                    title = "${mediaType.replaceFirstChar { 
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) 
                        else it.toString() 
                    }} Detail",
                    onBackClick = { navController.popBackStack() },
                    isVisible = isVisible
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (mediaType == "image") {
                    // Image viewer with zoom controls
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        scale = (scale * zoom).coerceIn(1f, 5f)
                                        val extraWidth = (scale - 1f) * (this.size.width / 2)
                                        val extraHeight = (scale - 1f) * (this.size.height / 2)
                                        offsetX = (offsetX + pan.x * scale).coerceIn(-extraWidth, extraWidth)
                                        offsetY = (offsetY + pan.y * scale).coerceIn(-extraHeight, extraHeight)
                                    }
                                }
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shadow(
                                        elevation = 24.dp,
                                        shape = RoundedCornerShape(24.dp),
                                        ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f),
                                        spotColor = Color(0xFF8B5CF6).copy(alpha = 0.3f)
                                    ),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF2D2D2D)
                                )
                            ) {
                                AsyncImage(
                                    model = mediaUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer(
                                            scaleX = scale,
                                            scaleY = scale,
                                            translationX = offsetX,
                                            translationY = offsetY
                                        )
                                )
                            }
                        }

                        // Zoom controls
                        if (mediaType == "image") {
                            ZoomControls(
                                scale = scale,
                                onZoomIn = { 
                                    if (scale < 5f) scale += 0.5f 
                                },
                                onZoomOut = { 
                                    if (scale > 1f) {
                                        scale -= 0.5f
                                        if (scale <= 1f) {
                                            scale = 1f
                                            offsetX = 0f
                                            offsetY = 0f
                                        }
                                    }
                                },
                                onReset = {
                                    scale = 1f
                                    offsetX = 0f
                                    offsetY = 0f
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 32.dp),
                                isVisible = isVisible
                            )
                        }
                    }
                } else if (mediaType == "video") {
                    // Video player with modern card
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .shadow(
                                    elevation = 24.dp,
                                    shape = RoundedCornerShape(24.dp),
                                    ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                                    spotColor = Color(0xFFEC4899).copy(alpha = 0.3f)
                                ),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF2D2D2D)
                            )
                        ) {
                            VideoPlayer(videoUrl = mediaUrl)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    isVisible: Boolean
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "TopBar Alpha"
    )

    TopAppBar(
        title = { 
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.graphicsLayer(alpha = animatedAlpha)
            ) 
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        ),
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(48.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f)
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFEC4899),
                                Color(0xFF8B5CF6)
                            )
                        )
                    )
                    .graphicsLayer(alpha = animatedAlpha),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1A1A1A).copy(alpha = 0.95f),
                    Color.Transparent
                )
            )
        )
    )
}

@Composable
fun ZoomControls(
    scale: Float,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 200),
        label = "Zoom Controls Alpha"
    )

    Card(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.3f)
            )
            .graphicsLayer(alpha = animatedAlpha),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D2D2D)
        )
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2D2D2D),
                            Color(0xFF3D3D3D),
                            Color(0xFF2D2D2D)
                        )
                    )
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Zoom Out Button
            ModernIconButton(
                icon = Icons.Filled.ZoomOut,
                onClick = onZoomOut,
                enabled = scale > 1f,
                gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6366F1))
            )

            // Scale indicator
            Text(
                text = "${(scale * 100).toInt()}%",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Zoom In Button
            ModernIconButton(
                icon = Icons.Filled.ZoomIn,
                onClick = onZoomIn,
                enabled = scale < 5f,
                gradientColors = listOf(Color(0xFFEC4899), Color(0xFFF97316))
            )

            // Reset Button
            if (scale > 1f) {
                TextButton(
                    onClick = onReset,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Reset",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ModernIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    enabled: Boolean,
    gradientColors: List<Color>
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .shadow(
                elevation = if (enabled) 8.dp else 2.dp,
                shape = CircleShape,
                ambientColor = if (enabled) gradientColors[0].copy(alpha = 0.3f) else Color.Transparent
            )
            .clip(CircleShape)
            .background(
                brush = if (enabled) {
                    Brush.linearGradient(gradientColors)
                } else {
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF4D4D4D),
                            Color(0xFF3D3D3D)
                        )
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) Color.White else Color(0xFF666666)
            )
        }
    }
}

@Composable
fun DetailDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Background Animation")

    val offsetX1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset X"
    )

    val offsetY1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1 Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Pink gradient orb
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (100 + offsetX1).dp, y = (-50 + offsetY1).dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x50EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Purple gradient orb
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-120 - offsetX1).dp, y = (300 - offsetY1).dp)
                .blur(90.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x458B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Cyan gradient orb
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = 60.dp, y = (500 + offsetY1).dp)
                .blur(70.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x3506B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    GauravActStudioTheme {
        DetailScreen(
            navController = rememberNavController(),
            mediaUrl = "https://via.placeholder.com/150",
            mediaType = "image"
        )
    }
}
