package com.kmpstudios.universalAppJc.domain.di.repository

import com.kmpstudios.universalAppJc.data.repository.AuthRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.LocationRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.MovieRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.UserRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.dataSource.local.LocationLocalDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.AuthRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.LocationRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.MovieRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.UserRemoteDataSource
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.repository.LocationRepository
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository
import com.kmpstudios.universalAppJc.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesLocationRepository(
        locationLocalDataSource: LocationLocalDataSource,
        locationRemoteDataSource: LocationRemoteDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(locationLocalDataSource, locationRemoteDataSource)
    }
}