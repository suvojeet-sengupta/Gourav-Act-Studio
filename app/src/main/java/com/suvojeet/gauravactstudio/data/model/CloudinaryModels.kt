package com.suvojeet.gauravactstudio.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a single resource/image from Cloudinary
 */
@Serializable
data class CloudinaryResource(
    @SerialName("public_id")
    val publicId: String,
    @SerialName("version")
    val version: Long,
    @SerialName("format")
    val format: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("type")
    val type: String,
    @SerialName("created_at")
    val createdAt: String
) {
    /**
     * Generate the full Cloudinary URL for this image
     */
    fun getImageUrl(cloudName: String, transformation: String = ""): String {
        val transformPart = if (transformation.isNotEmpty()) "$transformation/" else ""
        return "https://res.cloudinary.com/$cloudName/image/upload/$transformPart$publicId.$format"
    }
    
    /**
     * Get thumbnail URL (smaller size for grid views)
     */
    fun getThumbnailUrl(cloudName: String): String {
        return getImageUrl(cloudName, "c_fill,w_400,h_400,q_auto")
    }
    
    /**
     * Get full resolution URL for detail view
     */
    fun getFullUrl(cloudName: String): String {
        return getImageUrl(cloudName, "q_auto,f_auto")
    }
    
    /**
     * Calculate aspect ratio
     */
    val aspectRatio: Float
        get() = if (height > 0) width.toFloat() / height.toFloat() else 1f
}

/**
 * Response from Cloudinary resource list API
 */
@Serializable
data class CloudinaryListResponse(
    @SerialName("resources")
    val resources: List<CloudinaryResource>,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

/**
 * Represents an album (folder) in the gallery
 */
data class Album(
    val name: String,
    val displayName: String,
    val coverUrl: String,
    val photoCount: Int,
    val folderPath: String
)
