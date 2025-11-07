package com.suvojeet.gauravactstudio.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import androidx.compose.ui.res.stringResource
import com.suvojeet.gauravactstudio.R

data class Service(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradient: List<Color> = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
)

 @Composable
fun ServicesScreen() {
    val servicesList = listOf(
        Service(
            stringResource(R.string.service_wedding_photography_title),
            stringResource(R.string.service_wedding_photography_description),
            Icons.Filled.Camera,
            listOf(Color(0xFFEC4899), Color(0xFFF97316))
        ),
        Service(
            stringResource(R.string.service_ring_ceremony_title),
            stringResource(R.string.service_ring_ceremony_description),
            Icons.Filled.Favorite,
            listOf(Color(0xFFEF4444), Color(0xFFF59E0B))
        ),
        Service(
            stringResource(R.string.service_birthday_celebrations_title),
            stringResource(R.string.service_birthday_celebrations_description),
            Icons.Filled.CardGiftcard,
            listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
        ),
        Service(
            stringResource(R.string.service_pre_wedding_shoots_title),
            stringResource(R.string.service_pre_wedding_shoots_description),
            Icons.Filled.PhotoCamera,
            listOf(Color(0xFF06B6D4), Color(0xFF3B82F6))
        ),
        Service(
            stringResource(R.string.service_maternity_shoots_title),
            stringResource(R.string.service_maternity_shoots_description),
            Icons.Filled.ChildFriendly,
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
        ),
        Service(
            stringResource(R.string.service_baby_shoots_title),
            stringResource(R.string.service_baby_shoots_description),
            Icons.Filled.ChildFriendly,
            listOf(Color(0xFF10B981), Color(0xFF14B8A6))
        ),
        Service(
            stringResource(R.string.service_corporate_events_title),
            stringResource(R.string.service_corporate_events_description),
            Icons.Filled.CorporateFare,
            listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
        ),
        Service(
            stringResource(R.string.service_fashion_portfolio_title),
            stringResource(R.string.service_fashion_portfolio_description),
            Icons.Filled.Style,
            listOf(Color(0xFFEC4899), Color(0xFFA855F7))
        ),
        Service(
            stringResource(R.string.service_product_photography_title),
            stringResource(R.string.service_product_photography_description),
            Icons.Filled.Videocam,
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
        ),
        Service(
            stringResource(R.string.service_wedding_card_design_title),
            stringResource(R.string.service_wedding_card_design_description),
            Icons.Filled.Create,
            listOf(Color(0xFFF97316), Color(0xFFFBBF24))
        ),
        Service(
            stringResource(R.string.service_business_promotion_shoots_title),
            stringResource(R.string.service_business_promotion_shoots_description),
            Icons.Filled.Store,
            listOf(Color(0xFF4CAF50), Color(0xFF8BC34A))
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE0F7FA),
                        Color(0xFFFFF9C4)
                    )
                )
            )
    ) {
        // Header Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.services_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.services_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Services Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servicesList) { service ->
                ServiceCard(service = service)
            }
        }
    }
}

 @Composable
fun ServiceCard(service: Service) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gradient Icon Background
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(service.gradient)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = service.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Service Title
            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Service Description
            Text(
                text = service.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

 @Preview(showBackground = true)
 @Composable
fun ServicesScreenPreview() {
    GauravActStudioTheme {
        ServicesScreen()
    }
}