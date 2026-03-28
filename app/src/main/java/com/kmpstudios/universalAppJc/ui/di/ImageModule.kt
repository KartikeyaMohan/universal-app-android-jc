package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.screens.ImageScreen
import com.kmpstudios.universalAppJc.ui.navigation.Image
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object ImageModule {

    @IntoSet
    @Provides
    fun providesImageEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureImageEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureImageEntryBuilder() {
        entry<Image> {
            ImageScreen()
        }
    }
}