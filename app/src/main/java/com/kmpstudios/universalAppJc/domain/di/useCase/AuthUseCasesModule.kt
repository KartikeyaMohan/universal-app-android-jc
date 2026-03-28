package com.kmpstudios.universalAppJc.domain.di.useCase

import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.useCases.auth.LoginUserUseCase
import com.kmpstudios.universalAppJc.domain.useCases.auth.RefreshTokenUseCase
import com.kmpstudios.universalAppJc.domain.useCases.auth.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCasesModule {

    @Singleton
    @Provides
    fun providesRegisterUserUseCase(authRepository: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesLoginUserUseCase(authRepository: AuthRepository): LoginUserUseCase {
        return LoginUserUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesRefreshUserUseCase(authRepository: AuthRepository): RefreshTokenUseCase {
        return RefreshTokenUseCase(authRepository)
    }
}