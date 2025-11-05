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
        name = "Standard Package",
        price = "₹12,000",
        features = listOf(
            "30-sheet album (200 photos)",
            "32GB pen drive",
            "2 cameras for photo and video"
        )
    ),
    PricePackage(
        name = "Deluxe Package",
        price = "₹25,000",
        features = listOf(
            "2 cameras",
            "1 drone",
            "1-2 highlight videos",
            "32GB USB",
            "Album"
        )
    ),
    PricePackage(
        name = "Premium Package",
        price = "₹35,000",
        features = listOf(
            "Cinematic + Traditional Video & Photo",
            "Highlight Video",
            "2 Videographers",
            "2 Photographers"
        )
    ),
    PricePackage(
        name = "Elite Package",
        price = "₹55,000",
        features = listOf(
            "Pre-Wedding Shoot",
            "Ring Ceremony Shoot",
            "Wedding Shoot (Cinematic + Traditional)"
        )
    )
)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button

@Composable
fun PricingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Get Started")
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