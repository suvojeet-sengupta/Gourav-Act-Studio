package com.suvojeet.gauravactstudio.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Service(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradient: List<Color>,
    val isHighlighted: Boolean = false,
    val category: String = "All",
    val priceRange: String = "" // Added for pricing/detail info
)
