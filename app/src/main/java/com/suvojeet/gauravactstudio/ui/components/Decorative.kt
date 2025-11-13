package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LightDecorativeBackground(scrollOffset: Int = 0) {
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

        // Gradient orb 3 - Cyan
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 80.dp, y = (650 + offsetY1 - scrollOffset * 0.08f).dp)
                .blur(110.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x3006B6D4),
                            Color(0x0006B6D4)
                        )
                    )
                )
        )

        // Gradient orb 4 - Orange
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = 200.dp, y = (450 + offsetX1 + scrollOffset * 0.03f).dp)
                .blur(90.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x28F97316),
                            Color(0x00F97316)
                        )
                    )
                )
        )
    }
}
