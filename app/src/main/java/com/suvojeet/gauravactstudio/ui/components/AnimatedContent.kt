package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedContent(
    isVisible: Boolean,
    delay: Long = 0,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            delayMillis = delay.toInt(),
            easing = FastOutSlowInEasing
        )
    )
    
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.9f,
        animationSpec = tween(
            durationMillis = 600,
            delayMillis = delay.toInt(),
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .alpha(alpha)
            .scale(scale)
    ) {
        content()
    }
}