package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.navigation.Location
import com.kmpstudios.universalAppJc.ui.navigation.LocationTable
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.locations.LocationScreen
import com.kmpstudios.universalAppJc.ui.screens.locations.LocationTableScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object LocationModule {

    @IntoSet
    @Provides
    fun provideLocationEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureLocationEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureLocationEntryBuilder() {
        entry<Location> { LocationScreen() }
        entry<LocationTable> { LocationTableScreen() }
    }
}