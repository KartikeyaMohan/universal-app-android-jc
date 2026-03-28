package com.kmpstudios.universalAppJc.data.di

import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.AuthRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.LocationRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.MovieRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.UserRemoteDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.AuthRemoteDataSourceImpl
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.LocationRemoteDataSourceImpl
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.MovieRemoteDataSourceImpl
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Singleton
    @Provides
    fun providesAuthRemoteDataSource(apiService: ApiService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesUserRemoteDataSource(apiService: ApiService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(apiService: ApiService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesLocationRemoteDataSource(apiService: ApiService): LocationRemoteDataSource {
        return LocationRemoteDataSourceImpl(apiService)
    }
}