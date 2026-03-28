package com.kmpstudios.universalAppJc.domain.repository

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {

    suspend fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part?
    ): GenericResponse<LoginRegisterResponse>

    suspend fun login(loginRequest: LoginRequest): GenericResponse<LoginRegisterResponse>

    suspend fun refresh(): GenericResponse<LoginRegisterResponse>
}