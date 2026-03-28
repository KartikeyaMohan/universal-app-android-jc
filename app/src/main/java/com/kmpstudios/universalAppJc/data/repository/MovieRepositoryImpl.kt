package com.kmpstudios.universalAppJc.data.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.MovieRemoteDataSource
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
): BaseRepository(), MovieRepository {

    override suspend fun getMovies(
        page: Int,
        limit: Int
    ): GenericResponse<MovieResponse> = callApi {
        movieRemoteDataSource.getMovies(page, limit)
    }

    override suspend fun getMovieDetails(id: Long): GenericResponse<MovieDetailsResponse> = callApi {
        movieRemoteDataSource.getMovieDetails(id)
    }
}