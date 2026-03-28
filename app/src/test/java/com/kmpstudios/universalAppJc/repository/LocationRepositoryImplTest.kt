package com.kmpstudios.universalAppJc.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationData
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.repository.LocationRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.dataSource.local.LocationLocalDataSource
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.LocationRemoteDataSource
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class LocationRepositoryImplTest {

    private val locationLocalDataSource: LocationLocalDataSource = mockk(relaxed = true)
    private val locationRemoteDataSource: LocationRemoteDataSource = mockk()
    private val repository = LocationRepositoryImpl(locationLocalDataSource, locationRemoteDataSource)

    private val request = LocationRequest(
        listOf(LocationData("d", "t", 1.0, 2.0, 3f))
    )

    private val success = GenericResponse(status = 200, data = MessageResponse("ok"))

    @Test
    fun `postLocations returns success`() = runTest {
        coEvery { locationRemoteDataSource.postLocations(request) } returns Response.success(success)

        val result = repository.postLocations(request)

        assertTrue(result.isSuccess())
        assertEquals("ok", result.data?.message)
    }

    @Test
    fun `postLocations returns INTERNET_ISSUE on UnknownHostException`() = runTest {
        coEvery { locationRemoteDataSource.postLocations(any()) } throws UnknownHostException()

        val result = repository.postLocations(request)

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `postLocations returns error body on HTTP error`() = runTest {
        coEvery { locationRemoteDataSource.postLocations(any()) } returns Response.error(
            400,
            "Bad".toResponseBody()
        )

        val result = repository.postLocations(request)

        assertEquals(400, result.status)
    }
}
