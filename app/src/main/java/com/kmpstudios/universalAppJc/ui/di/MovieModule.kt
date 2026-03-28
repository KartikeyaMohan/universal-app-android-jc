package com.kmpstudios.universalAppJc.ui.di

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.MovieDetails
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.movies.MovieDetailScreen
import com.kmpstudios.universalAppJc.ui.screens.movies.MovieScreen
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object MovieModule {

    @IntoSet
    @Provides
    fun provideMovieEntryBuilder(): EntryProviderScope<NavKey>.() -> Unit = {
        featureMovieEntryBuilder()
    }

    fun EntryProviderScope<NavKey>.featureMovieEntryBuilder() {
        entry<Movie> { MovieScreen() }
        entry<MovieDetails> { key ->
            MovieDetailScreen(
                movieDetailsViewModel = hiltViewModel(
                    creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                        factory.create(key.id)
                    }
                )
            )
        }
    }
}