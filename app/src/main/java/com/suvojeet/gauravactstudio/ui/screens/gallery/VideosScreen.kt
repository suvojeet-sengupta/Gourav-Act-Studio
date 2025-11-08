package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.components.VideoPlayer // Import the new VideoPlayer

@Composable
fun VideosScreen(modifier: Modifier = Modifier) {
    val businessPromotionVideoUrl = "https://drive.google.com/uc?export=download&id=1Jg8eOA0AgvjOdyzAKsiQExI0PZBBgtbq"
    // Note: This Google Drive link (`/uc?export=download`) is a direct download link and is more likely to work with ExoPlayer.
    // If the video still doesn't play, ensure the file permissions on Google Drive allow public access.

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.service_business_promotion_shoots_title), // Using existing string for title
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        VideoPlayer(videoUrl = businessPromotionVideoUrl)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "More videos coming soon!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
