package com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.AuthRemoteDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class AuthRemoteDataSourceImpl(private val apiService: ApiService): AuthRemoteDataSource {

    override suspend fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part?
    ): Response<GenericResponse<LoginRegisterResponse>> =
        apiService.register(name, email, password, profileImage)

    override suspend fun login(loginRequest: LoginRequest): Response<GenericResponse<LoginRegisterResponse>> =
        apiService.login(loginRequest)

    override suspend fun refresh(): Response<GenericResponse<LoginRegisterResponse>> =
        apiService.refresh()
}