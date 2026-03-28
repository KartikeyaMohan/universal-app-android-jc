package com.kmpstudios.universalAppJc.domain.di.useCase

import com.kmpstudios.universalAppJc.domain.repository.UserRepository
import com.kmpstudios.universalAppJc.domain.useCases.users.GetUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    @Singleton
    @Provides
    fun providesGetUserProfileUseCase(userRepository: UserRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository)
    }
}