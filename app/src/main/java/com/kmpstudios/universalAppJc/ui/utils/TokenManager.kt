package com.kmpstudios.universalAppJc.ui.utils

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val authPrefs = "auth_prefs"
    private val authToken = "auth_token"
    private val refreshToken = "refresh_token"

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        authPrefs,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getAuthToken(): String? {
        return encryptedSharedPreferences.getString(authToken, null)
    }

    fun getRefreshToken(): String? {
        return encryptedSharedPreferences.getString(refreshToken, null)
    }

    fun saveTokens(authToken: String, refreshToken: String) {
        encryptedSharedPreferences.edit {
            putString(this@TokenManager.authToken, authToken)
            putString(this@TokenManager.refreshToken, refreshToken)
        }
    }

    fun clearAuthToken() {
        encryptedSharedPreferences.edit { remove(authToken) }
    }

    fun clearTokens() {
        encryptedSharedPreferences.edit {
            remove(authToken)
            remove(refreshToken)
        }
    }
}