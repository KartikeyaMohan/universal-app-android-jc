package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.domain.useCases.auth.LoginUserUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val loginUserUseCase: LoginUserUseCase
): ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginRegisterResponse?>(null)
    val loginResponse = _loginResponse.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = loginUserUseCase.execute(LoginRequest(email, password))
            if (response.isSuccess()) {
                if (response.data?.tokens?.authToken.isNullOrEmpty().not() &&
                    response.data.tokens.refreshToken.isNullOrEmpty().not()) {
                    tokenManager.saveTokens(response.data.tokens.authToken, response.data.tokens.refreshToken)
                }
                _loginResponse.value = response.data
            }
        }
    }
}