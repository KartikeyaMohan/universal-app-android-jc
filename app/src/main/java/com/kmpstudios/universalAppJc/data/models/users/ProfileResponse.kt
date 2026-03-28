package com.kmpstudios.universalAppJc.data.models.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("name")
    val name: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("profile_image_url")
    val profileImageUrl: String? = null
)
