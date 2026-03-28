package com.kmpstudios.universalAppJc.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.repository.MovieRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.MovieRemoteDataSource
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private val movieRemoteDataSource: MovieRemoteDataSource = mockk()
    private val repository = MovieRepositoryImpl(movieRemoteDataSource)

    private val fakeMovies = listOf(MovieData(1L, "Tenet", "", 4.0f))
    private val fakeMovieResponse = GenericResponse(
        status = 200,
        data = MovieResponse(list = fakeMovies, meta = PagerMetaData(null, 2))
    )
    private val fakeDetailsResponse = GenericResponse(
        status = 200,
        data = MovieDetailsResponse(
            1L,
            "Tenet",
            "",
            emptyList(),
            "",
            4.0F,
            emptyList(),
            ""
        )
    )

    @Test
    fun `getMovies returns success with data`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(1, 10)
        } returns Response.success(fakeMovieResponse)

        val result = repository.getMovies(1, 10)

        assertTrue(result.isSuccess())
        assertEquals(fakeMovies, result.data?.list)
    }

    @Test
    fun `getMovies returns error status code on HTTP error`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(1, 10)
        } returns Response.error(404, "Not Found".toResponseBody())

        val result = repository.getMovies(1, 10)

        assertFalse(result.isSuccess())
        assertEquals(404, result.status)
    }

    @Test
    fun `getMovies returns INTERNET_ISSUE on UnknownHostException`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(any(), any())
        } throws UnknownHostException()

        val result = repository.getMovies(1, 10)

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `getMovies returns SLOW_INTERNET on SocketTimeoutException`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(any(), any())
        } throws SocketTimeoutException()

        val result = repository.getMovies(1, 10)

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.SLOW_INTERNET, result.errors?.first()?.code)
    }

    @Test
    fun `getMovies returns KOTLINX_SERIALIZATION on parse error`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(any(), any())
        } throws kotlinx.serialization.SerializationException("Bad JSON")

        val result = repository.getMovies(1, 10)

        assertEquals(StatusCodes.UNPROCESSABLE_ENTITY, result.status)
        assertEquals(ErrorTypes.KOTLINX_SERIALIZATION, result.errors?.first()?.code)
    }

    @Test
    fun `getMovies returns DEFAULT_ERROR on unknown exception`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovies(any(), any())
        } throws RuntimeException("Unknown")

        val result = repository.getMovies(1, 10)

        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, result.status)
        assertEquals(ErrorTypes.DEFAULT_ERROR, result.errors?.first()?.code)
    }

    @Test
    fun `getMovieDetails returns success with data`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovieDetails(1L)
        } returns Response.success(fakeDetailsResponse)

        val result = repository.getMovieDetails(1L)

        assertTrue(result.isSuccess())
        assertEquals("Tenet", result.data?.name)
    }

    @Test
    fun `getMovieDetails returns INTERNET_ISSUE on UnknownHostException`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovieDetails(any())
        } throws UnknownHostException()

        val result = repository.getMovieDetails(1L)

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `getMovieDetails returns SLOW_INTERNET on SocketTimeoutException`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovieDetails(any())
        } throws SocketTimeoutException()

        val result = repository.getMovieDetails(1L)

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.SLOW_INTERNET, result.errors?.first()?.code)
    }

    @Test
    fun `getMovieDetails returns KOTLINX_SERIALIZATION on parse error`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovieDetails(any())
        } throws kotlinx.serialization.SerializationException("Bad JSON")

        val result = repository.getMovieDetails(1L)

        assertEquals(StatusCodes.UNPROCESSABLE_ENTITY, result.status)
        assertEquals(ErrorTypes.KOTLINX_SERIALIZATION, result.errors?.first()?.code)
    }

    @Test
    fun `getMovieDetails returns DEFAULT_ERROR on unknown exception`() = runTest {
        coEvery {
            movieRemoteDataSource.getMovieDetails(any())
        } throws IllegalStateException("Unexpected")

        val result = repository.getMovieDetails(1L)

        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, result.status)
        assertEquals(ErrorTypes.DEFAULT_ERROR, result.errors?.first()?.code)
    }
}