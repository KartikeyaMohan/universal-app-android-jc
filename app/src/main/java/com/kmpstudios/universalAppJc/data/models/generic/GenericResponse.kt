package com.kmpstudios.universalAppJc.data.models.generic

import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("data")
    val data: T? = null,
    @SerialName("errors")
    val errors: List<ErrorData>? = null
) {
    fun isSuccess() = status == StatusCodes.SUCCESS || status == StatusCodes.CREATED

    fun isUnauthorized() = status == StatusCodes.UNAUTHORIZED
}
