package com.suvojeet.gauravactstudio.ui.components

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvojeet.gauravactstudio.Screen
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import kotlinx.coroutines.delay

@Composable
fun PortfolioCard(
    item: PortfolioItem,
    navController: NavController,
    isCategory: Boolean = false,
    onShareClick: ((PortfolioItem) -> Unit)? = null
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
                var isLiked by remember { mutableStateOf(false) }
                val haptic = LocalHapticFeedback.current
                
                var animatedScale by remember { mutableStateOf(1f) }
                LaunchedEffect(isLiked) {
                    if (isLiked) {
                        animatedScale = 1.3f
                        delay(100)
                        animatedScale = 1f
                    }
                }

                val finalScale by animateFloatAsState(
                    targetValue = animatedScale,
                    animationSpec = spring(
                        dampingRatio = androidx.compose.animation.core.Spring.DampingRatioHighBouncy,
                        stiffness = androidx.compose.animation.core.Spring.StiffnessMedium
                    ),
                    label = "FinalHeartScale"
                )
                
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row {
                        IconButton(
                            onClick = { 
                                isLiked = !isLiked
                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                                .scale(finalScale)
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isLiked) Color(0xFFEC4899) else Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        
                        onShareClick?.let { onShare ->
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { onShare(item) },
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
}
