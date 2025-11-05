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
import com.suvojeet.gouravactstudio.ui.theme.AccentColor
import com.suvojeet.gouravactstudio.ui.theme.GouravActStudioTheme

data class PricePackage(val name: String, val price: String, val features: List<String>)

val pricingList = listOf(
    PricePackage(
        name = "Basic Package",
        price = "₹12,000",
        features = listOf(
            "4 Hours Coverage",
            "1 Photographer",
            "100 Edited Photos",
            "Online Gallery"
        )
    ),
    PricePackage(
        name = "Standard Package",
        price = "₹25,000",
        features = listOf(
            "8 Hours Coverage",
            "1 Photographer, 1 Videographer",
            "250 Edited Photos",
            "Highlight Video (3-5 min)",
            "Online Gallery & USB Drive"
        )
    ),
    PricePackage(
        name = "Premium Package",
        price = "₹50,000",
        features = listOf(
            "Full Day Coverage",
            "2 Photographers, 2 Videographers",
            "500+ Edited Photos",
            "Cinematic Video (10-15 min)",
            "Pre-Wedding Shoot",
            "Custom Album & USB Drive"
        )
    )
)

@Composable
fun PricingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Our Pricing",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(pricingList) { pricePackage ->
                PricePackageCard(pricePackage = pricePackage)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PricePackageCard(pricePackage: PricePackage, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pricePackage.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = pricePackage.price,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = AccentColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                pricePackage.features.forEach { feature ->
                    Text(
                        text = "• $feature",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PricingScreenPreview() {
    GouravActStudioTheme {
        PricingScreen()
    }
}