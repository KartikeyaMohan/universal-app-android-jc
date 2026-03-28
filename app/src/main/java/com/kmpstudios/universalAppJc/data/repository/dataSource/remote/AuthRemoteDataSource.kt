package com.kmpstudios.universalAppJc.data.repository.dataSource.remote

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part?
    ): Response<GenericResponse<LoginRegisterResponse>>

    suspend fun login(
        loginRequest: LoginRequest
    ): Response<GenericResponse<LoginRegisterResponse>>

    suspend fun refresh(): Response<GenericResponse<LoginRegisterResponse>>
}