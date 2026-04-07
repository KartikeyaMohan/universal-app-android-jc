package com.kmpstudios.universalAppJc.data.di

import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.kmpstudios.universalAppJc.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val contentType: MediaType by lazy { "application/json".toMediaType() }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json/*(strictMode = false))*/{
            isLenient = true
            ignoreUnknownKeys = true
        }
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
        return retrofitBuilder.build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        headersInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
        if (BuildConfig.DEBUG) {
            okHttpClient
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesHeadersInspector(tokenManager: TokenManager) = Interceptor { chain ->
        try {
            val authToken = tokenManager.getAuthToken()
            val chainBuilder = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
            if (authToken.isNullOrEmpty().not()) {
                chainBuilder
                    .addHeader("Authorization", "Bearer $authToken")
            }
            else {
                val refreshToken = tokenManager.getRefreshToken()
                if (refreshToken.isNullOrEmpty().not()) {
                    chainBuilder
                        .addHeader("X-Refresh-Token", refreshToken)
                }
            }
            chain.proceed(chainBuilder.build())
        } catch (exception: Exception) {
            throw okio.IOException(exception)
        }
    }
}