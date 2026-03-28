package com.kmpstudios.universalAppJc.data.models.generic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    @SerialName("code")
    val code: String? = null,
    @SerialName("message")
    val message: String? = null
)
