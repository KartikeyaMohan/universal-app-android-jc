package com.kmpstudios.universalAppJc.data.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    @SerialName("auth_token")
    val authToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
)
