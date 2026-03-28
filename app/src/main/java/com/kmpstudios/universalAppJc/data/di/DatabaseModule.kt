package com.kmpstudios.universalAppJc.data.di

import android.content.Context
import androidx.room.Room
import com.kmpstudios.universalAppJc.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providesLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()
}