package com.kmpstudios.universalAppJc.domain.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object DataTypeHelper {

    fun String.toRequestBody(): RequestBody {
        val mediaType = "text/plain".toMediaTypeOrNull()
        return this.toRequestBody(mediaType)
    }

    fun File?.toMultipartBody(fileKey: String): MultipartBody.Part? {
        if (this == null) {
            return null
        }
        val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fileKey, this.name, requestFile)
    }
}