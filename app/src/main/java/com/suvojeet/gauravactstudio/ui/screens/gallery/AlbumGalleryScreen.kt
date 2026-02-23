package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.suvojeet.gauravactstudio.data.CloudinaryService
import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.components.shimmerEffect
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumGalleryScreen(navController: NavController, modifier: Modifier = Modifier) {
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50)
        isVisible = true
        
        val result = CloudinaryService.getAlbums()
        if (result.isSuccess) {
            albums = result.getOrNull() ?: emptyList()
        } else {
            error = result.exceptionOrNull()?.message ?: "Failed to load albums"
        }
        isLoading = false
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
        // Decorative Background Orbs
        AlbumDecorativeBackground()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFEC4899),
                            modifier = Modifier.size(48.dp)
                        )
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
                                text = "No albums found",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF666666)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF999999),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(albums) { album ->
                            AnimatedContent(isVisible, delay = 100) {
                                AlbumCard(
                                    album = album,
                                    onClick = {
                                        navController.navigate("album_photos/${album.name}")
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
fun AlbumCard(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.2f)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Album Cover Image
            if (album.coverUrl.isNotEmpty()) {
                var isLoading by remember { mutableStateOf(true) }
                AsyncImage(
                    model = album.coverUrl,
                    contentDescription = album.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (isLoading) Modifier.shimmerEffect() else Modifier),
                    onSuccess = { isLoading = false },
                    onError = { isLoading = false }
                )
            } else {
                // Placeholder when no cover
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 200f
                        )
                    )
            )

            // Album Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = album.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${album.photoCount} Photos",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumDecorativeBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "Album BG Animation")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb Offset X"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb Offset Y"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Pink Orb
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (100 + offsetX).dp, y = (-50 + offsetY).dp)
                .blur(90.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x35EC4899),
                            Color(0x00EC4899)
                        )
                    )
                )
        )

        // Purple Orb
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-100 - offsetX).dp, y = (300 - offsetY).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x308B5CF6),
                            Color(0x008B5CF6)
                        )
                    )
                )
        )

        // Cyan Orb
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (200 + offsetX / 2).dp, y = (500 + offsetY).dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x2506B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )
    }
}
