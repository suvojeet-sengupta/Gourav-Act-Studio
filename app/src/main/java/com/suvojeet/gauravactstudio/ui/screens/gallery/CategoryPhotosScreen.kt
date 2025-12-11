package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.components.AnimatedContent
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import kotlinx.coroutines.delay

@Composable
fun CategoryPhotosScreen(navController: NavController, category: String, modifier: Modifier = Modifier) {
    // Manually added estimated aspect ratios
    val allPortfolioItems = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczNlECP759-_slbJhEi1br7Occb7fOkMkxNQpOg4OP7CEP9-Kt4t4NWoZ1CP9IE1OphbK1MBrXRvODRjUezRp3w4yMjQctIdP-pttKOtNlKMljm2-nh4cbN1dw_Pv1tfzf0BPS-hNsA1HAHmGnJwg5RJPw=w853-h1280-s-no-gm?authuser=0", 0.67f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczN9QPJB3fIn8pGGAyjtI-wfSAAA2dMoQsnOng0qcTAqdwy30k0LzU2ySQWneD1D2vNb5oIPWQuE84sKhqJ9yUWlaalwi1NTaV3-Pp-mO7DpZ04-vK2uolMHrvFPuqbCyj6AIm53z5LaFvxRHZX3rq11Gg=w683-h1024-s-no-gm?authuser=0", 0.67f),
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0", 0.8f)
    )
    val portfolioItems = allPortfolioItems.filter { it.title == category }

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
                        Color(0xFFF5F5F7)
                    )
                )
            )
    ) {
        LightDecorativeBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            AnimatedContent(isVisible) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 32.sp,
                        letterSpacing = 0.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(isVisible, delay = 100) {
                val context = LocalContext.current // Get context here
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(portfolioItems) { item ->
                        PortfolioCard(
                            item = item,
                            navController = navController,
                            onShareClick = { photoItem ->
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Check out this amazing photo from Gaurav Act Studio: ${photoItem.imageUrl}")
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

@Composable
private fun LightDecorativeBackground(scrollOffset: Int = 0) {
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
    }
}

@Composable
fun PortfolioCard(item: PortfolioItem, navController: NavController, isCategory: Boolean = false, onShareClick: (PortfolioItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Use aspect ratio from item, or 1f if it's a category card
            .aspectRatio(if (isCategory) 1.2f else item.aspectRatio)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (isCategory) {
                    navController.navigate("category_photos/${item.title}")
                } else {
                    navController.navigate(Screen.Detail.createRoute("image", item.imageUrl))
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            var isLoading by remember { mutableStateOf(true) }
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = if (isLoading) Modifier.fillMaxSize().shimmerEffect() else Modifier.fillMaxSize(),
                onSuccess = { isLoading = false },
                onError = { isLoading = false }
            )

            // Title overlay
            if (isCategory) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Heart Icon for Photos
                var isLiked by remember { mutableStateOf(false) }
                
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row {
                        IconButton(
                            onClick = { isLiked = !isLiked },
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isLiked) Color(0xFFEC4899) else Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp)) // Space between icons
                        IconButton(
                            onClick = { onShareClick(item) },
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "Shimmer Transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer Offset"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}