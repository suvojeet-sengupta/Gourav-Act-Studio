package com.suvojeet.gauravactstudio.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import com.suvojeet.gauravactstudio.data.CloudinaryService
import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.data.model.CloudinaryResource
import com.suvojeet.gauravactstudio.data.model.Service
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import com.suvojeet.gauravactstudio.ui.model.VideoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository to centralize data access.
 * Combines hardcoded "static" data (which acts as our database for now)
 * with dynamic data from Cloudinary.
 */
object StudioRepository {

    // --- Static Data (Replacing scattered mockups) ---

    val services = listOf(
        Service(
            "Wedding Photography",
            "Capturing the most beautiful moments of your special day with cinematic excellence.",
            Icons.Filled.Camera,
            listOf(Color(0xFFEC4899), Color(0xFFF97316)),
            category = "Wedding",
            isHighlighted = true
        ),
        Service(
            "Pre-Wedding Shoots",
            "Romantic and creative pre-wedding shoots at exotic locations.",
            Icons.Filled.PhotoCamera,
            listOf(Color(0xFF06B6D4), Color(0xFF3B82F6)),
            category = "Wedding"
        ),
        Service(
            "Cinematic Films",
            "High-quality cinematic wedding films and teasers.",
            Icons.Filled.Videocam,
            listOf(Color(0xFF8B5CF6), Color(0xFF6366F1)),
            category = "Wedding"
        ),
        Service(
            "Birthday & Events",
            "Professional coverage for birthdays, anniversaries, and corporate events.",
            Icons.Filled.Cake,
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24)),
            category = "Events"
        ),
        Service(
            "Maternity Shoots",
            "Beautiful maternity portraits to cherish forever.",
            Icons.Filled.ChildFriendly,
            listOf(Color(0xFF10B981), Color(0xFF14B8A6)),
            category = "Events"
        ),
         Service(
            "Baby Shoots",
            "Adorable newborn and baby photography sessions.",
            Icons.Filled.Face,
            listOf(Color(0xFFFFA726), Color(0xFFFF7043)),
            category = "Events"
        ),
        Service(
            "Fashion & Portfolio",
            "Professional portfolio shoots for models and actors.",
            Icons.Filled.Style,
            listOf(Color(0xFFEC4899), Color(0xFFA855F7)),
            category = "Corporate"
        ),
        Service(
            "Product Photography",
            "High-quality product shoots for e-commerce and branding.",
            Icons.Filled.ShoppingBag,
            listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
            category = "Corporate"
        )
    )

    val banners = listOf(
        BannerData(
            "Event Excellence",
            "Capture your special moments with our professional team",
            listOf(Color(0xFFF59E0B), Color(0xFFFBBF24)),
            "services"
        ),
        BannerData(
            "Portfolio Perfection",
            "Get a professional portfolio that stands out",
            listOf(Color(0xFFEC4899), Color(0xFFA855F7)),
            "gallery"
        )
    )

    val portfolioCategories = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0", 1.5f),
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0", 0.8f)
    )

    // Fallback videos if Cloudinary fetch fails
    private val fallbackVideos = listOf(
         VideoItem(
            title = "Wedding Cinematic Teaser",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/0.jpg", 
            videoUrl = "https://drive.google.com/uc?export=download&id=1Jg8eOA0AgvjOdyzAKsiQExI0PZBBgtbq" 
        )
    )

    // --- Dynamic Data Access ---

    suspend fun getAlbums(): Result<List<Album>> {
        return withContext(Dispatchers.IO) {
            CloudinaryService.getAlbums()
        }
    }

    suspend fun getFeaturedPhotos(): Result<List<CloudinaryResource>> {
        return withContext(Dispatchers.IO) {
            // Try to fetch from a specific "featured" folder or just the main album
            CloudinaryService.getPhotosFromFolder("featured")
                .recover { 
                    // Fallback to main album if featured doesn't exist
                    CloudinaryService.getAlbumPhotos().getOrNull()?.take(5) ?: emptyList()
                }
        }
    }
    
    suspend fun getVideos(): Result<List<VideoItem>> {
        return withContext(Dispatchers.IO) {
            // In a real app, we'd fetch from Cloudinary here. 
            // Since we are adding video support to CloudinaryService, we can try that.
            val cloudVideos = CloudinaryService.getVideos("videos") // Assume "videos" tag
            
            if (cloudVideos.isSuccess && !cloudVideos.getOrNull().isNullOrEmpty()) {
                val items = cloudVideos.getOrNull()!!.map { res ->
                    VideoItem(
                        title = "Studio Video", // Cloudinary list response doesn't give nice titles usually
                        thumbnailUrl = res.getThumbnailUrl(CloudinaryService.CLOUD_NAME),
                        videoUrl = res.getImageUrl(CloudinaryService.CLOUD_NAME) // It returns the video URL
                    )
                }
                Result.success(items)
            } else {
                Result.success(fallbackVideos)
            }
        }
    }
}

data class BannerData(
    val title: String,
    val subtitle: String,
    val gradient: List<Color>,
    val targetRoute: String
)

