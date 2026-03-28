package com.kmpstudios.universalAppJc.data.repository

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.AuthRemoteDataSource
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
): BaseRepository(), AuthRepository {

    override suspend fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part?
    ): GenericResponse<LoginRegisterResponse> = callApi {
        authRemoteDataSource.register(
            name, email, password, profileImage
        )
    }

    override suspend fun login(loginRequest: LoginRequest): GenericResponse<LoginRegisterResponse> =
        callApi {
            authRemoteDataSource.login(loginRequest)
        }

    override suspend fun refresh(): GenericResponse<LoginRegisterResponse> =
        callApi {
            authRemoteDataSource.refresh()
        }
}