package com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val apiService: ApiService
): MovieRemoteDataSource {

    override suspend fun getMovies(
        page: Int,
        limit: Int
    ): Response<GenericResponse<MovieResponse>> = apiService.getMovies(page, limit)

    override suspend fun getMovieDetails(id: Long): Response<GenericResponse<MovieDetailsResponse>> =
        apiService.getMovieDetails(id)
}