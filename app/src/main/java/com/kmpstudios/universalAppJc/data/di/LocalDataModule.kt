package com.kmpstudios.universalAppJc.data.di

import com.kmpstudios.universalAppJc.data.db.LocationDao
import com.kmpstudios.universalAppJc.data.repository.dataSource.local.LocationLocalDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.local.LocationLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun providesLocationLocalDataSource(locationDao: LocationDao): LocationLocalDataSource {
        return LocationLocalDataSourceImpl(locationDao)
    }
}