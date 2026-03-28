package com.kmpstudios.universalAppJc.domain.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toMultipartBody
import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toRequestBody
import java.io.File

class RegisterUserUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(name: String, email: String, password: String, profileImage: File?): GenericResponse<LoginRegisterResponse> =
        authRepository.register(
            name.toRequestBody(),
            email.toRequestBody(),
            password.toRequestBody(),
            profileImage?.toMultipartBody("profile_image")
        )
}