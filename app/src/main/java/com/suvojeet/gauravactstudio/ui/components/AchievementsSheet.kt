package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AchievementsSheetContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 50.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = null,
                tint = Color(0xFFFFD700), // Gold color
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Our Achievements",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )
        }

        // Introduction
        Text(
            text = "Over the years, Gaurav Act Studio has reached remarkable milestones, driven by our passion for creativity and client satisfaction.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF4B5563),
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stats Grid
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AchievementItem(
                icon = Icons.Filled.Groups,
                count = "500+",
                label = "Happy Clients",
                color = Color(0xFFEC4899)
            )
            AchievementItem(
                icon = Icons.Filled.Videocam,
                count = "1000+",
                label = "Events Covered",
                color = Color(0xFF8B5CF6)
            )
            AchievementItem(
                icon = Icons.Filled.Star,
                count = "4.9/5",
                label = "Average Rating",
                color = Color(0xFFF59E0B)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Awards or Recognitions
        Text(
            text = "Recognitions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "• Best Photography Studio in Firozabad (2024)\n• Featured in Top Visual Arts Vendors\n• 10 Years of Excellence in Visual Arts",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF4B5563),
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2937))
        ) {
            Text("Close", color = Color.White)
        }
    }
}

@Composable
fun AchievementItem(
    icon: ImageVector,
    count: String,
    label: String,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = count,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
}
