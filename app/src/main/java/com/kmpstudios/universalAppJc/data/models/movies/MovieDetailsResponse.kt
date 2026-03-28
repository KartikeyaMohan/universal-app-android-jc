package com.kmpstudios.universalAppJc.data.models.movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String? = null,
    @SerialName("hero_image_url")
    val heroImageUrl: String? = null,
    @SerialName("image_urls")
    val imageUrls: List<String>? = null,
    @SerialName("trailer_url")
    val trailerUrl: String? = null,
    @SerialName("rating")
    val rating: Float? = null,
    @SerialName("casts")
    val casts: List<Cast>? = null,
    @SerialName("description")
    val description: String? = null
)
