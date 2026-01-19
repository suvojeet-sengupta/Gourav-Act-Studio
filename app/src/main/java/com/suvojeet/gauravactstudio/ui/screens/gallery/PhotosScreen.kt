package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.data.CloudinaryService
import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import kotlinx.coroutines.delay

@Composable
fun PhotosScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Portfolio Items for Categories
    val portfolioItems = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0", 0.8f)
    )
    
    val categories = portfolioItems.groupBy { it.title }.map { (title, items) ->
        PortfolioItem(title, items.first().imageUrl, 1.2f)
    }

    // Album state - try cached first
    var albums by remember { mutableStateOf(CloudinaryService.getCachedAlbums() ?: emptyList()) }
    var albumsLoading by remember { mutableStateOf(CloudinaryService.getCachedAlbums() == null) }
    var isVisible by remember { mutableStateOf(false) }
    
    // Scroll state for controlling scroll position
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    LaunchedEffect(Unit) {
        // Scroll to top when screen opens
        listState.scrollToItem(0)
        
        delay(20)
        isVisible = true
        
        // Only fetch if no cache
        if (CloudinaryService.getCachedAlbums() == null) {
            val result = CloudinaryService.getAlbums()
            if (result.isSuccess) {
                albums = result.getOrNull() ?: emptyList()
            }
            albumsLoading = false
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Album Gallery Section
        item {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(300)) + slideInVertically(initialOffsetY = { -it / 4 })
            ) {
                AlbumGallerySection(
                    albums = albums,
                    isLoading = albumsLoading,
                    navController = navController
                )
            }
        }

        // Categories Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF8B5CF6).copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Collections,
                            contentDescription = null,
                            tint = Color(0xFF8B5CF6),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                }
            }
        }

        // Categories Grid
        item {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 14.dp,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height((categories.size / 2 * 180 + 100).dp),
                userScrollEnabled = false
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    AnimatedStaggeredItem(visible = isVisible, index = index) {
                        PortfolioCard(
                            item = category,
                            navController = navController,
                            isCategory = true,
                            onShareClick = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumGallerySection(
    albums: List<Album>,
    isLoading: Boolean,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEC4899).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhotoLibrary,
                        contentDescription = null,
                        tint = Color(0xFFEC4899),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Album Gallery",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }
            
            TextButton(
                onClick = { navController.navigate(Screen.AlbumGallery.route) }
            ) {
                Text(
                    text = "View All",
                    color = Color(0xFFEC4899),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFFEC4899),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Album Cards - Horizontal Scroll
        if (isLoading) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                repeat(2) {
                    AlbumCardSkeleton()
                }
            }
        } else if (albums.isEmpty()) {
            // Empty state - Show a card to explore album
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AlbumPreviewCard(
                    title = "Album Gallery",
                    subtitle = "89 Photos",
                    coverUrl = "https://res.cloudinary.com/dujg9rmfh/image/upload/v1/album%20library/sample",
                    onClick = { navController.navigate("album_photos/album library") }
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                albums.forEach { album ->
                    AlbumPreviewCard(
                        title = album.displayName,
                        subtitle = "${album.photoCount} Photos",
                        coverUrl = album.coverUrl,
                        onClick = { navController.navigate("album_photos/${album.name}") }
                    )
                }
            }
        }
    }
}

@Composable
private fun AlbumPreviewCard(
    title: String,
    subtitle: String,
    coverUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFEC4899).copy(alpha = 0.15f)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Cover Image or Gradient
            if (coverUrl.isNotEmpty()) {
                var isLoading by remember { mutableStateOf(true) }
                AsyncImage(
                    model = coverUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (isLoading) Modifier.shimmerEffect() else Modifier),
                    onSuccess = { isLoading = false },
                    onError = { isLoading = false }
                )
            } else {
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
                        modifier = Modifier.size(40.dp)
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
                            startY = 50f
                        )
                    )
            )

            // Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(14.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Arrow Icon
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun AlbumCardSkeleton() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}

@Composable
fun AnimatedStaggeredItem(
    visible: Boolean,
    index: Int,
    content: @Composable () -> Unit
) {
    val enterAnimation = remember(visible) {
        if (visible) {
            fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = index * 50)) +
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) +
                    scaleIn(
                        initialScale = 0.8f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
        } else {
            EnterTransition.None
        }
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = enterAnimation
    ) {
        content()
    }
}
