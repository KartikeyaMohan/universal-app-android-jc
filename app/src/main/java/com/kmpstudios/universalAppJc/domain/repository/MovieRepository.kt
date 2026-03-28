package com.kmpstudios.universalAppJc.domain.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse

interface MovieRepository {
    suspend fun getMovies(page: Int, limit: Int): GenericResponse<MovieResponse>
    suspend fun getMovieDetails(id: Long): GenericResponse<MovieDetailsResponse>
}