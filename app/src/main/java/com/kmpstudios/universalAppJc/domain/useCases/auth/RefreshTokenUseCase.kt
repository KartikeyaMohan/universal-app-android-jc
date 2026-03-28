package com.kmpstudios.universalAppJc.domain.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository

class RefreshTokenUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(): GenericResponse<LoginRegisterResponse> =
        authRepository.refresh()
}