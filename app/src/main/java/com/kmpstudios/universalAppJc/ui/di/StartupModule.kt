package com.kmpstudios.universalAppJc.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.navigation.Login
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.navigation.Register
import com.kmpstudios.universalAppJc.ui.screens.startup.LoginScreen
import com.kmpstudios.universalAppJc.ui.screens.startup.RegisterScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object StartupModule {

    @IntoSet
    @Provides
    fun providesStartupEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureStartupEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureStartupEntryBuilder() {
        entry<Login> { LoginScreen() }
        entry<Register> { RegisterScreen() }
    }
}