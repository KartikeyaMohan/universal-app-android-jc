package com.kmpstudios.universalAppJc.data.repository.dataSource.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun getProfile(): Response<GenericResponse<ProfileResponse>>
}