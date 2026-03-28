package com.kmpstudios.universalAppJc.data.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRegisterResponse(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("tokens")
    val tokens: TokenData? = null
)
