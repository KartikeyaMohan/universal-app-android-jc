package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.ProfileScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object ProfileModule {

    @IntoSet
    @Provides
    fun provideProfileEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureProfileEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureProfileEntryBuilder() {
        entry<Profile> {
            ProfileScreen()
        }
    }
}