package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.domain.useCases.auth.RegisterUserUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val registerUserUseCase: RegisterUserUseCase,
): ViewModel() {

    private val _registerResponse = MutableStateFlow<LoginRegisterResponse?>(null)
    val registerResponse = _registerResponse.asStateFlow()

    fun register(name: String, email: String, password: String, profileImage: File? = null) {
        viewModelScope.launch {
            val response = registerUserUseCase.execute(name, email, password, profileImage)
            if (response.isSuccess()) {
                if (response.data?.tokens?.authToken.isNullOrEmpty().not() &&
                    response.data.tokens.refreshToken.isNullOrEmpty().not()) {
                    tokenManager.saveTokens(response.data.tokens.authToken, response.data.tokens.refreshToken)
                }
                _registerResponse.value = response.data
            }
        }
    }
}