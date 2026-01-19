package com.suvojeet.gauravactstudio.ui.screens.gallery

import android.content.Intent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.data.CloudinaryService
import com.suvojeet.gauravactstudio.data.model.CloudinaryResource
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumPhotosScreen(navController: NavController, albumName: String, modifier: Modifier = Modifier) {
    // Try cached photos first
    val cachedPhotos = CloudinaryService.getCachedPhotos()
    var photos by remember { mutableStateOf(cachedPhotos ?: emptyList()) }
    var isLoading by remember { mutableStateOf(cachedPhotos == null) }
    var error by remember { mutableStateOf<String?>(null) }
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(albumName) {
        delay(50)
        isVisible = true
        
        // Only fetch if no cache
        if (cachedPhotos == null) {
            val result = CloudinaryService.getPhotosFromFolder(albumName)
            if (result.isSuccess) {
                photos = result.getOrNull() ?: emptyList()
            } else {
                error = result.exceptionOrNull()?.message ?: "Failed to load photos"
            }
            isLoading = false
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
        // Decorative Background
        AlbumPhotosDecorativeBackground()

        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            TopAppBar(
                title = {
                    Text(
                        text = "Album Gallery",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1A1A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            // Photo count header
            if (!isLoading && error == null && photos.isNotEmpty()) {
                AnimatedContent(isVisible, delay = 50) {
                    Text(
                        text = "${photos.size} Photos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = Color(0xFFEC4899),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading photos...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.PhotoLibrary,
                                contentDescription = null,
                                tint = Color(0xFFEC4899).copy(alpha = 0.5f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No photos found",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF666666)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Please tag your images with 'album_library' on Cloudinary",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF999999),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                }
                else -> {
                    AnimatedContent(isVisible, delay = 100) {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalItemSpacing = 16.dp,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(photos) { photo ->
                                val portfolioItem = PortfolioItem(
                                    title = photo.publicId.substringAfterLast("/"),
                                    imageUrl = photo.getFullUrl(CloudinaryService.CLOUD_NAME),
                                    aspectRatio = photo.aspectRatio
                                )
                                PortfolioCard(
                                    item = portfolioItem,
                                    navController = navController,
                                    onShareClick = { item ->
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, "Check out this amazing photo from Gaurav Act Studio: ${item.imageUrl}")
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Share Photo"))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumPhotosDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Photos BG Animation")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb Offset X"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Pink Orb
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (120 + offsetX).dp, y = (-60 + offsetY).dp)
                .blur(85.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x38EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Purple Orb
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = (-120 - offsetX).dp, y = (350 - offsetY).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x2D8B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )
    }
}
