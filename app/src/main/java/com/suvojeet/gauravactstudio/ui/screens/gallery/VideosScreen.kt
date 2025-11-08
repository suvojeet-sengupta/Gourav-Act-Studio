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
    val businessPromotionVideoUrl = "https://drive.google.com/file/d/1Jg8eOA0AgvjOdyzAKsiQExI0PZBBgtbq/view?usp=drivesdk"
    // Note: Google Drive links (especially 'view?usp=drivesdk') are generally not direct video streams.
    // ExoPlayer requires a direct URL to the video file (e.g., an .mp4 file).
    // If the video doesn't play, a direct MP4 or streaming URL would be needed.

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
