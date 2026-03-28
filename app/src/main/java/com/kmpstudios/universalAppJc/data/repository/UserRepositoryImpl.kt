package com.kmpstudios.universalAppJc.data.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.UserRemoteDataSource
import com.kmpstudios.universalAppJc.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): BaseRepository(), UserRepository {

    override suspend fun getProfile(): GenericResponse<ProfileResponse> =
        callApi {
            userRemoteDataSource.getProfile()
        }
}