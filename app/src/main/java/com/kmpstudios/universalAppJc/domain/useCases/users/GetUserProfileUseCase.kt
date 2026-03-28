package com.kmpstudios.universalAppJc.domain.useCases.users

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.domain.repository.UserRepository

class GetUserProfileUseCase(private val userRepository: UserRepository) {

    suspend fun execute(): GenericResponse<ProfileResponse> =
        userRepository.getProfile()
}