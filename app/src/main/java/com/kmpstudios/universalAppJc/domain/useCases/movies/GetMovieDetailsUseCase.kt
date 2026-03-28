package com.kmpstudios.universalAppJc.domain.useCases.movies

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository

class GetMovieDetailsUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(id: Long): GenericResponse<MovieDetailsResponse> {
        return movieRepository.getMovieDetails(id)
    }
}