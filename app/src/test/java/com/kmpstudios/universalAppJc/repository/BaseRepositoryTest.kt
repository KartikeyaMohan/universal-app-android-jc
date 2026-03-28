package com.kmpstudios.universalAppJc.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.data.repository.BaseRepository
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class BaseRepositoryTest {

    private val repository = object : BaseRepository() { }

    private val fakeData = MovieDetailsResponse(
        id = 1L, name = "Tenet", description = "Thriller", rating = 4.0F,
        trailerUrl = "", casts = emptyList()
    )

    private val fakeGenericResponse = GenericResponse(status = 200, data = fakeData)

    //---------------------- Happy Path ----------------------

    @Test
    fun `callApi returns body when response is successful`() = runTest {
        val result = repository.callApi {
            Response.success(fakeGenericResponse)
        }

        assertTrue(result.isSuccess())
        assertEquals(fakeData, result.data)
        assertEquals(200, result.status)
    }

    @Test
    fun `callApi returns empty GenericResponse when body is null`() = runTest {
        val result = repository.callApi {
            Response.success<GenericResponse<MovieDetailsResponse>>(204, null)
        }

        assertNull(result.data)
        assertEquals(204, result.status)
    }

    @Test
    fun `callApi returns error body status code when response is not successful`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            Response.error(404, "Not Found".toResponseBody("application/json".toMediaType()))
        }

        assertEquals(404, result.status)
        assertNull(result.data)
    }

    //---------------------- Exception Handling ----------------------

    @Test
    fun `callApi handles UnknownHostException with INTERNET_ISSUE error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw UnknownHostException("Unable to resolve host")
        }

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles ConnectException with INTERNET_ISSUE error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw ConnectException("Connection refused")
        }

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles UnknownHostException wrapped in cause with INTERNET_ISSUE error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw Exception(Exception(UnknownHostException("Wrapped")))
        }

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles SocketTimeoutException with SLOW_INTERNET error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw SocketTimeoutException("Timeout")
        }

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.SLOW_INTERNET, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles SocketTimeoutException wrapped in cause with SLOW_INTERNET error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw Exception(Exception(SocketTimeoutException("Wrapped timeout")))
        }

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.SLOW_INTERNET, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles SerializationException with KOTLINX_SERIALIZATION error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw kotlinx.serialization.SerializationException("Parse error")
        }

        assertEquals(StatusCodes.UNPROCESSABLE_ENTITY, result.status)
        assertEquals(ErrorTypes.KOTLINX_SERIALIZATION, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles SerializationException wrapped in cause with KOTLINX_SERIALIZATION error`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw Exception(Exception(kotlinx.serialization.SerializationException("Wrapped parse error")))
        }

        assertEquals(StatusCodes.UNPROCESSABLE_ENTITY, result.status)
        assertEquals(ErrorTypes.KOTLINX_SERIALIZATION, result.errors?.first()?.code)
    }

    @Test
    fun `callApi handles unknown exception with DEFAULT_ERROR`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw RuntimeException("Something unexpected")
        }

        assertEquals(StatusCodes.INTERNAL_SERVER_ERROR, result.status)
        assertEquals(ErrorTypes.DEFAULT_ERROR, result.errors?.first()?.code)
    }

    @Test
    fun `callApi error response contains exactly one error entry`() = runTest {
        val result = repository.callApi<MovieDetailsResponse> {
            throw UnknownHostException()
        }

        assertEquals(1, result.errors?.size)
    }
}