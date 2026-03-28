package com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(private val apiService: ApiService): UserRemoteDataSource {

    override suspend fun getProfile(): Response<GenericResponse<ProfileResponse>> =
        apiService.getProfile()
}