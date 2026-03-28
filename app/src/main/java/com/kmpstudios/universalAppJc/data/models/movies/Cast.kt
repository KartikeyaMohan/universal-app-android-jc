package com.kmpstudios.universalAppJc.data.models.movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    @SerialName("id")
    val id: Long,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("cast_type")
    val castType: String? = null
)
