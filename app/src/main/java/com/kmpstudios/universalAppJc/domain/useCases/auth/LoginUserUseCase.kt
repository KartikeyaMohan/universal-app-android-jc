package com.kmpstudios.universalAppJc.domain.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository

class LoginUserUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(loginRequest: LoginRequest): GenericResponse<LoginRegisterResponse> =
        authRepository.login(loginRequest)
}