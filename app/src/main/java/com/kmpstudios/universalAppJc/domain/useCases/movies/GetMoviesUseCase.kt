package com.kmpstudios.universalAppJc.domain.useCases.movies

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository

class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(page: Int, limit: Int): GenericResponse<MovieResponse> {
        return movieRepository.getMovies(page, limit)
    }
}