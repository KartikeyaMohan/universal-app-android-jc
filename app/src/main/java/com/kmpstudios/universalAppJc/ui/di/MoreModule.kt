package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.screens.MoreScreen
import com.kmpstudios.universalAppJc.ui.navigation.More
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object MoreModule {

    @IntoSet
    @Provides
    fun provideMoreEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureMoreHomeBuilder()
    }

    fun EntryProviderScope<NavKey>.featureMoreHomeBuilder() {
        entry<More> {
            MoreScreen()
        }
    }
}