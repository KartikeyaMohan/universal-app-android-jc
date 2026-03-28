package com.kmpstudios.universalAppJc.domain.di.useCase

import com.kmpstudios.universalAppJc.domain.repository.MovieRepository
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMovieDetailsUseCase
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieUseCasesModule {

    @Singleton
    @Provides
    fun providesGetMoviesUseCase(movieRepository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun providesGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository)
    }
}