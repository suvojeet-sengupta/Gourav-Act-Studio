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

data class Service(val title: String, val description: String)

val servicesList = listOf(
    Service("Wedding Photography & Videography", "Capturing every precious moment of your special day with elegance and artistry."),
    Service("Ring Ceremony Shoot", "Beautifully documenting the start of your journey together."),
    Service("Birthday Celebrations", "Making your birthday memories last a lifetime with vibrant photos and videos."),
    Service("Pre-Wedding Shoots", "Creative and romantic shoots to tell your unique love story."),
    Service("Maternity Shoots", "Cherishing the beautiful journey of motherhood with tender photographs."),
    Service("Baby Shoots", "Adorable and heartwarming captures of your little one's early days."),
    Service("Corporate Events", "Professional coverage for your business events, conferences, and parties."),
    Service("Fashion & Portfolio Shoots", "Showcasing your style and personality with stunning visuals."),
    Service("Product Photography", "High-quality images to highlight your products.")
)

@Composable
fun ServicesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(servicesList) { service ->
            ServiceCard(service = service)
            Spacer(modifier = Modifier.height(16.dp))
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
