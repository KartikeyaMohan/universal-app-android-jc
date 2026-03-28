package com.kmpstudios.universalAppJc.dataSource

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.MessageResponse
import com.kmpstudios.universalAppJc.data.models.locations.LocationData
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.LocationRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LocationRemoteDataSourceImplTest {

    private val apiService: ApiService = mockk()
    private val dataSource = LocationRemoteDataSourceImpl(apiService)

    private val request = LocationRequest(listOf(LocationData("d", "t", 0.0, 0.0, 1f)))
    private val body = GenericResponse(status = 200, data = MessageResponse("synced"))

    @Test
    fun `postLocations delegates to apiService`() = runTest {
        coEvery { apiService.postLocations(request) } returns Response.success(body)

        val result = dataSource.postLocations(request)

        assertEquals(body, result.body())
        coVerify(exactly = 1) { apiService.postLocations(request) }
    }
}
