package com.kmpstudios.universalAppJc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.domain.useCases.users.GetUserProfileUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val tokenManager: TokenManager
): ViewModel() {
    private val _profileResponse = MutableStateFlow<ProfileResponse?>(null)
    val profileResponse = _profileResponse.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val response = getUserProfileUseCase.execute()
            if (response.isSuccess()) {
                _profileResponse.value = response.data
            }
        }
    }

    fun clearTokens() {
        tokenManager.clearTokens()
    }
}