package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.screens.home.HomeScreen
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule {

    @IntoSet
    @Provides
    fun provideHomeEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureHomeEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureHomeEntryBuilder() {
        entry<Home> {
            HomeScreen()
        }
    }
}