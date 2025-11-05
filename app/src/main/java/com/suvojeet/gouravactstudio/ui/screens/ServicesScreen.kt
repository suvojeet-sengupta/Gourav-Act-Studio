package com.suvojeet.gouravactstudio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suvojeet.gouravactstudio.ui.theme.GouravActStudioTheme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.graphics.vector.ImageVector

data class Service(val title: String, val description: String, val icon: ImageVector)

val servicesList = listOf(
    Service("Wedding Photography & Videography", "We offer both cinematic and traditional styles of videography and photography to capture every precious moment of your special day with elegance and artistry.", Icons.Filled.Camera),
    Service("Ring Ceremony Shoot", "Beautifully documenting the start of your journey together.", Icons.Filled.Favorite),
    Service("Birthday Celebrations", "Making your birthday memories last a lifetime with vibrant photos and videos. We cover all types of birthday shoots.", Icons.Filled.CardGiftcard),
    Service("Pre-Wedding Shoots", "Creative and romantic shoots to tell your unique love story.", Icons.Filled.PhotoCamera),
    Service("Maternity Shoots", "Cherishing the beautiful journey of motherhood with tender photographs.", Icons.Filled.ChildFriendly),
    Service("Baby Shoots", "Adorable and heartwarming captures of your little one's early days.", Icons.Filled.ChildFriendly),
    Service("Corporate Events", "Professional coverage for your business events, conferences, and parties.", Icons.Filled.CorporateFare),
    Service("Fashion & Portfolio Shoots", "Showcasing your style and personality with stunning visuals.", Icons.Filled.Style),
    Service("Product Photography", "High-quality images to highlight your products.", Icons.Filled.Videocam),
    Service("Wedding Card Design", "We design beautiful and unique wedding cards to match your style.", Icons.Filled.Create)
)

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon

@Composable
fun ServicesScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(servicesList) { service ->
            ServiceCard(service = service)
        }
    }
}

@Composable
fun ServiceCard(service: Service) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = service.title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    GouravActStudioTheme {
        ServicesScreen()
    }
}
