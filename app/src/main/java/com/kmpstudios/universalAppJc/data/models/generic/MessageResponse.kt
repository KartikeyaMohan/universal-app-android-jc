package com.kmpstudios.universalAppJc.data.models.generic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    @SerialName("message")
    val message: String? = null
)
