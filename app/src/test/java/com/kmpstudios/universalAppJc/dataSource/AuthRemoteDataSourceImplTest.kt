package com.kmpstudios.universalAppJc.dataSource

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.network.ApiService
import com.kmpstudios.universalAppJc.data.repository.dataSourceImpl.remote.AuthRemoteDataSourceImpl
import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toRequestBody
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRemoteDataSourceImplTest {

    private val apiService: ApiService = mockk()
    private val dataSource = AuthRemoteDataSourceImpl(apiService)

    private val loginBody = GenericResponse(
        status = 200,
        data = LoginRegisterResponse(tokens = TokenData("a", "r"))
    )

    @Test
    fun `login delegates to apiService`() = runTest {
        val req = LoginRequest("e", "p")
        coEvery { apiService.login(req) } returns Response.success(loginBody)

        val result = dataSource.login(req)

        assertEquals(loginBody, result.body())
        coVerify(exactly = 1) { apiService.login(req) }
    }

    @Test
    fun `refresh delegates to apiService`() = runTest {
        coEvery { apiService.refresh() } returns Response.success(loginBody)

        val result = dataSource.refresh()

        assertEquals(loginBody, result.body())
        coVerify(exactly = 1) { apiService.refresh() }
    }

    @Test
    fun `register delegates to apiService`() = runTest {
        coEvery {
            apiService.register(any(), any(), any(), any())
        } returns Response.success(loginBody)

        val result = dataSource.register(
            "n".toRequestBody(),
            "e".toRequestBody(),
            "p".toRequestBody(),
            null
        )

        assertEquals(loginBody, result.body())
        coVerify(exactly = 1) { apiService.register(any(), any(), any(), null) }
    }
}
