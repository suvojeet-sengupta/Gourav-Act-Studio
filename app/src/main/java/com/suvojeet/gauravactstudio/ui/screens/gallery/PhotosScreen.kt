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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem

@Composable
fun PhotosScreen(navController: NavController, modifier: Modifier = Modifier) {
    val portfolioItems = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0"),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczNlECP759-_slbJhEi1br7Occb7fOkMkxNQpOg4OP7CEP9-Kt4t4NWoZ1CP9IE1OphbK1MBrXRvODRjUezRp3w4yMjQctIdP-pttKOtNlKMljm2-nh4cbN1dw_Pv1tfzf0BPS-hNsA1HAHmGnJwg5RJPw=w853-h1280-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczN9QPJB3fIn8pGGAyjtI-wfSAAA2dMoQsnOng0qcTAqdwy30k0LzU2ySQWneD1D2vNb5oIPWQuE84sKhqJ9yUWlaalwi1NTaV3-Pp-mO7DpZ04-vK2uolMHrvFPuqbCyj6AIm53z5LaFvxRHZX3rq11Gg=w683-h1024-s-no-gm?authuser=0"),
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0")
    )
    val categories = portfolioItems.groupBy { it.title }.map { (title, items) ->
        PortfolioItem(title, items.first().imageUrl)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(top = 16.dp)
    ) {
        items(categories) { category ->
            PortfolioCard(item = category, navController = navController, isCategory = true)
        }
    }
}

@Composable
fun PortfolioCard(item: PortfolioItem, navController: NavController, isCategory: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (isCategory) {
                    navController.navigate("category_photos/${item.title}")
                } else {
                    navController.navigate(Screen.Detail.createRoute("image", item.imageUrl))
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            var isLoading by remember { mutableStateOf(true) }
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = if (isLoading) Modifier.fillMaxSize().shimmerEffect() else Modifier.fillMaxSize(),
                onSuccess = { isLoading = false },
                onError = { isLoading = false }
            )

            // Overlay gradient + text
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
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
