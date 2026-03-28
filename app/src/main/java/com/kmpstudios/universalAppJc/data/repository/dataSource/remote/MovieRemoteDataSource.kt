package com.kmpstudios.universalAppJc.data.repository.dataSource.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int, limit: Int): Response<GenericResponse<MovieResponse>>
    suspend fun getMovieDetails(id: Long): Response<GenericResponse<MovieDetailsResponse>>
}