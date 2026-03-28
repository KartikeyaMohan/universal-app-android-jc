package com.kmpstudios.universalAppJc.dataSource

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.UserRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class UserRemoteDataSourceImplTest {

    private val apiService: ApiService = mockk()
    private val dataSource = UserRemoteDataSourceImpl(apiService)

    private val profile = GenericResponse(
        status = 200,
        data = ProfileResponse("N", "n@test.com", null)
    )

    @Test
    fun `getProfile delegates to apiService`() = runTest {
        coEvery { apiService.getProfile() } returns Response.success(profile)

        val result = dataSource.getProfile()

        assertEquals(profile, result.body())
        coVerify(exactly = 1) { apiService.getProfile() }
    }
}
