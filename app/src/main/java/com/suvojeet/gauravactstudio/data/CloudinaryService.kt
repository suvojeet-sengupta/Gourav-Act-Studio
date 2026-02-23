package com.suvojeet.gauravactstudio.data

import com.suvojeet.gauravactstudio.data.model.Album
import com.suvojeet.gauravactstudio.data.model.CloudinaryListResponse
import com.suvojeet.gauravactstudio.data.model.CloudinaryResource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import android.content.Context
import com.suvojeet.gauravactstudio.util.NetworkConnectivityObserver

/**
 * Service for fetching images from Cloudinary with in-memory caching
 * 
 * Note: This uses Cloudinary's client-side resource list feature.
 * Make sure "Resource list" is enabled in Cloudinary Console -> Settings -> Security
 */
object CloudinaryService {

    private var connectivityObserver: NetworkConnectivityObserver? = null

    fun initialize(context: Context) {
        if (connectivityObserver == null) {
            connectivityObserver = NetworkConnectivityObserver(context)
        }
    }
    
    // Your Cloudinary Cloud Name
    const val CLOUD_NAME = "dujg9rmfh"
    
    // Album configuration - folder name on Cloudinary
    private const val ALBUM_FOLDER = "album library"
    
    // Cache for photos - keyed by folder name/tag
    private val cachedPhotosMap = ConcurrentHashMap<String, List<CloudinaryResource>>()
    private val lastFetchTimeMap = ConcurrentHashMap<String, Long>()
    
    private var cachedAlbums: List<Album>? = null
    private var lastAlbumsFetchTime: Long = 0
    private const val CACHE_DURATION_MS = 5 * 60 * 1000L // 5 minutes cache
    
    // Create Ktor HTTP client with Android engine
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    
    /**
     * Check if cache is still valid for a specific key
     */
    private fun isCacheValid(key: String): Boolean {
        val lastTime = lastFetchTimeMap[key] ?: 0
        return cachedPhotosMap.containsKey(key) && 
               (System.currentTimeMillis() - lastTime) < CACHE_DURATION_MS
    }

    private fun isAlbumsCacheValid(): Boolean {
        return cachedAlbums != null && 
               (System.currentTimeMillis() - lastAlbumsFetchTime) < CACHE_DURATION_MS
    }
    
    /**
     * Clear the cache - call this to force refresh
     */
    fun clearCache() {
        cachedPhotosMap.clear()
        lastFetchTimeMap.clear()
        cachedAlbums = null
        lastAlbumsFetchTime = 0
    }
    
    /**
     * Get list of albums with caching
     * Returns cached data if available, otherwise fetches from API
     */
    suspend fun getAlbums(): Result<List<Album>> {
        // Return cached albums if valid
        if (isAlbumsCacheValid() && cachedAlbums != null) {
            return Result.success(cachedAlbums!!)
        }
        
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
                val albums = listOf(album)
                cachedAlbums = albums
                lastAlbumsFetchTime = System.currentTimeMillis()
                Result.success(albums)
            } else {
                Result.failure(photos.exceptionOrNull() ?: Exception("Failed to load albums"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get all photos from a specific folder using Cloudinary's tag-based listing
     * Uses caching to prevent repeated API calls
     */
    suspend fun getPhotosFromFolder(folder: String): Result<List<CloudinaryResource>> {
        val tagName = folder.replace(" ", "_").lowercase()
        
        // Return cached photos if valid
        if (isCacheValid(tagName)) {
            return Result.success(cachedPhotosMap[tagName]!!)
        }

        if (connectivityObserver?.isConnected() == false) {
            return Result.failure(Exception("No internet connection"))
        }
        
        return try {
            // Try to fetch using tag-based listing
            // Format: https://res.cloudinary.com/{cloud_name}/image/list/{tag}.json
            val url = "https://res.cloudinary.com/$CLOUD_NAME/image/list/$tagName.json"
            
            val response: CloudinaryListResponse = client.get(url).body()
            
            // Cache the result
            cachedPhotosMap[tagName] = response.resources
            lastFetchTimeMap[tagName] = System.currentTimeMillis()
            
            Result.success(response.resources)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get list of videos from a specific folder (tag)
     */
    suspend fun getVideos(tag: String): Result<List<CloudinaryResource>> {
        return try {
            // Format: https://res.cloudinary.com/{cloud_name}/video/list/{tag}.json
            val tagName = tag.replace(" ", "_").lowercase()
            val url = "https://res.cloudinary.com/$CLOUD_NAME/video/list/$tagName.json"
            
            val response: CloudinaryListResponse = client.get(url).body()
            Result.success(response.resources)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get photos from the main album library (with caching)
     */
    suspend fun getAlbumPhotos(): Result<List<CloudinaryResource>> {
        return getPhotosFromFolder(ALBUM_FOLDER)
    }
    
    /**
     * Get cached photos synchronously if available
     * Returns null if no cache exists
     */
    fun getCachedPhotos(): List<CloudinaryResource>? {
        return if (isCacheValid()) cachedPhotos else null
    }
    
    /**
     * Get cached albums synchronously if available
     */
    fun getCachedAlbums(): List<Album>? {
        return if (isCacheValid()) cachedAlbums else null
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
