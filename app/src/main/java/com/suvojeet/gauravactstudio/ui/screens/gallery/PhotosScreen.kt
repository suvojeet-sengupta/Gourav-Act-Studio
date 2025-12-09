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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import com.suvojeet.gauravactstudio.ui.screens.gallery.PortfolioCard
import kotlinx.coroutines.delay

@Composable
fun PhotosScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Manually estimated aspect ratios based on typical camera outputs (3:2 = 1.5, 2:3 = 0.67)
    val portfolioItems = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczNlECP759-_slbJhEi1br7Occb7fOkMkxNQpOg4OP7CEP9-Kt4t4NWoZ1CP9IE1OphbK1MBrXRvODRjUezRp3w4yMjQctIdP-pttKOtNlKMljm2-nh4cbN1dw_Pv1tfzf0BPS-hNsA1HAHmGnJwg5RJPw=w853-h1280-s-no-gm?authuser=0", 0.67f), // Portrait
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczN9QPJB3fIn8pGGAyjtI-wfSAAA2dMoQsnOng0qcTAqdwy30k0LzU2ySQWneD1D2vNb5oIPWQuE84sKhqJ9yUWlaalwi1NTaV3-Pp-mO7DpZ04-vK2uolMHrvFPuqbCyj6AIm53z5LaFvxRHZX3rq11Gg=w683-h1024-s-no-gm?authuser=0", 0.67f), // Portrait
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0", 0.8f) // Nearly portrait
    )
    val categories = portfolioItems.groupBy { it.title }.map { (title, items) ->
        // For categories, we just use a standard 1.0f (square) or 1.3f (slightly wide)
        PortfolioItem(title, items.first().imageUrl, 1.2f)
    }

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
        // ... (Background content if any)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                AnimatedStaggeredItem(visible = isVisible, index = index) {
                    PortfolioCard(item = category, navController = navController, isCategory = true)
                }
            }
        }
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
                        initialOffsetY = { it / 2 }, // Half of content height
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

// PortfolioCard and shimmerEffect have been moved to CategoryPhotosScreen.kt
