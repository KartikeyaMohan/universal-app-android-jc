package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpstudios.universalAppJc.domain.useCases.auth.RefreshTokenUseCase
import com.kmpstudios.universalAppJc.domain.useCases.users.GetUserProfileUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
): ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isAuthenticated = MutableStateFlow<Boolean?>(false)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            if (tokenManager.getAuthToken().isNullOrEmpty()) {
                _isAuthenticated.value = false
                _isReady.value = true
                return@launch
            }

            val response = getUserProfileUseCase.execute()
            if (response.isSuccess()) {
                _isAuthenticated.value = true
                _isReady.value = true
                return@launch
            }
            else if (response.isUnauthorized()) {
                tokenManager.clearAuthToken()
                val refreshResponse = refreshTokenUseCase.execute()
                if (refreshResponse.isSuccess() &&
                    refreshResponse.data?.tokens?.authToken.isNullOrEmpty().not() &&
                    refreshResponse.data.tokens.refreshToken.isNullOrEmpty().not()) {
                    tokenManager.saveTokens(
                        refreshResponse.data.tokens.authToken,
                        refreshResponse.data.tokens.refreshToken
                    )
                    _isAuthenticated.value = true
                    _isReady.value = true
                }
                else {
                    tokenManager.clearTokens()
                    _isAuthenticated.value = false
                    _isReady.value = true
                }
            }
            else {
                _isAuthenticated.value = false
                _isReady.value = true
            }
        }
    }
}