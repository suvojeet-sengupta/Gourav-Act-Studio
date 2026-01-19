package com.suvojeet.gauravactstudio.data

import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.data.model.CloudinaryListResponse
import com.suvojeet.gauravactstudio.data.model.CloudinaryResource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Service for fetching images from Cloudinary
 * 
 * Note: This uses Cloudinary's client-side resource list feature.
 * Make sure "Resource list" is enabled in Cloudinary Console -> Settings -> Security
 */
object CloudinaryService {
    
    // Your Cloudinary Cloud Name
    const val CLOUD_NAME = "dujg9rmfh"
    
    // Album configuration - folder name on Cloudinary
    private const val ALBUM_FOLDER = "album library"
    
    // Create Ktor HTTP client
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    
    /**
     * Get list of albums (currently returns single album from folder)
     * In future, this can be expanded to support multiple folders
     */
    suspend fun getAlbums(): Result<List<Album>> {
        return try {
            val photos = getPhotosFromFolder(ALBUM_FOLDER)
            if (photos.isSuccess) {
                val resources = photos.getOrNull() ?: emptyList()
                val album = Album(
                    name = ALBUM_FOLDER,
                    displayName = "Album Gallery",
                    coverUrl = resources.firstOrNull()?.getThumbnailUrl(CLOUD_NAME) ?: "",
                    photoCount = resources.size,
                    folderPath = ALBUM_FOLDER
                )
                Result.success(listOf(album))
            } else {
                Result.failure(photos.exceptionOrNull() ?: Exception("Failed to load albums"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get all photos from a specific folder using Cloudinary's tag-based listing
     * 
     * Note: For this to work, you need to either:
     * 1. Tag images with the folder name when uploading, OR
     * 2. Use the Admin API (requires server-side implementation)
     * 
     * Alternative approach: Use direct folder URL structure
     */
    suspend fun getPhotosFromFolder(folder: String): Result<List<CloudinaryResource>> {
        return try {
            // Try to fetch using tag-based listing
            // Format: https://res.cloudinary.com/{cloud_name}/image/list/{tag}.json
            val tagName = folder.replace(" ", "_").lowercase()
            val url = "https://res.cloudinary.com/$CLOUD_NAME/image/list/$tagName.json"
            
            val response: CloudinaryListResponse = client.get(url).body()
            Result.success(response.resources)
        } catch (e: Exception) {
            // If tag-based listing fails, return empty list
            // User may need to tag their images or use Admin API
            Result.failure(e)
        }
    }
    
    /**
     * Get photos from the main album library
     */
    suspend fun getAlbumPhotos(): Result<List<CloudinaryResource>> {
        return getPhotosFromFolder(ALBUM_FOLDER)
    }
    
    /**
     * Generate a list URL for searching by prefix (folder path)
     * Note: This requires the Admin API and won't work client-side
     */
    fun getSearchUrl(prefix: String): String {
        return "https://res.cloudinary.com/$CLOUD_NAME/image/list/$prefix.json"
    }
    
    /**
     * Build a Cloudinary image URL with transformations
     */
    fun buildImageUrl(
        publicId: String,
        format: String = "jpg",
        transformation: String = ""
    ): String {
        val transformPart = if (transformation.isNotEmpty()) "$transformation/" else ""
        return "https://res.cloudinary.com/$CLOUD_NAME/image/upload/$transformPart$publicId.$format"
    }
    
    /**
     * Build optimized thumbnail URL
     */
    fun buildThumbnailUrl(publicId: String, format: String = "jpg"): String {
        return buildImageUrl(publicId, format, "c_fill,w_400,h_400,q_auto,f_auto")
    }
    
    /**
     * Build full quality URL for detail view
     */
    fun buildFullUrl(publicId: String, format: String = "jpg"): String {
        return buildImageUrl(publicId, format, "q_auto,f_auto")
    }
}
