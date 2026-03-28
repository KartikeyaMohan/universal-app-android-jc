package com.kmpstudios.universalAppJc.dataSource

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.MovieRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRemoteDataSourceImplTest {

    private val apiService: ApiService = mockk()
    private val dataSource = MovieRemoteDataSourceImpl(apiService)

    private val fakeMovieResponse = GenericResponse(
        status = 200,
        data = MovieResponse(
            list = listOf(MovieData(1L, "Tenet", "", 4.0F)),
            meta = PagerMetaData(prevPage = null, nextPage = 2)
        )
    )

    private val fakeMovieDetailsResponse = GenericResponse(
        status = 200,
        data = MovieDetailsResponse(
            id = 1L, name = "Tenet", description = "Thriller", rating = 4.0F,
            trailerUrl = "", casts = emptyList()
        )
    )

    @Test
    fun `getMovies delegates to apiService correctly`() = runTest {
        coEvery {
            apiService.getMovies(1, 10)
        } returns Response.success(fakeMovieResponse)

        val result = dataSource.getMovies(1, 10)

        assertEquals(fakeMovieResponse, result.body())
        coVerify(exactly = 1) { apiService.getMovies(1, 10) }
    }

    @Test
    fun `getMovieDetails delegates to apiService correctly`() = runTest {
        coEvery {
            apiService.getMovieDetails(1L)
        } returns Response.success(fakeMovieDetailsResponse)

        val result = dataSource.getMovieDetails(1L)

        assertEquals(fakeMovieDetailsResponse, result.body())
        coVerify(exactly = 1) { apiService.getMovieDetails(1L) }
    }
}