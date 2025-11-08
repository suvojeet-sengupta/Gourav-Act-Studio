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
    val businessPromotionVideoUrl = "https://photos.google.com/share/AF1QipOyysrfN2yX848f4YmnAwNkDlFrCiexYvohKJmB5SQZKyyFgPMyhB2w8sw6WSpUQA/photo/AF1QipNK3C_yYvPdidTappc_x5AeppyRfmi-SwY6YFt-?key=VkdodUtNZFRVVl9SU0t1ck5PaVhsbjl2eGdNd2RB"
    // Note: Google Photos links might not be direct video streams.
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
