package com.kmpstudios.universalAppJc.domain.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse

interface UserRepository {

    suspend fun getProfile(): GenericResponse<ProfileResponse>
}