package com.kmpstudios.universalAppJc.utils

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.activities.App
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.HomeScreen
import com.kmpstudios.universalAppJc.ui.screens.MoreScreen
import com.kmpstudios.universalAppJc.ui.screens.movies.MovieScreen
import com.kmpstudios.universalAppJc.ui.theme.UniversalAppTheme

fun ComposeContentTestRule.setAppContent(
    isAuthenticated: Boolean = true,
    entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit> = setOf(
        { entry<Home> { HomeScreen() } },
        { entry<Profile> { MoreScreen() } },
        { entry<Movie> { MovieScreen() } }
    )
) {
    setContent {
        UniversalAppTheme {
            App(
                entryBuilders = entryBuilders,
                isAuthenticated = isAuthenticated
            )
        }
    }
}