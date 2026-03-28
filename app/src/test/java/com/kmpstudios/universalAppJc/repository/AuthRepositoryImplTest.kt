package com.kmpstudios.universalAppJc.repository

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.repository.AuthRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.AuthRemoteDataSource
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import com.kmpstudios.universalAppJc.domain.utils.DataTypeHelper.toRequestBody
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
class AuthRepositoryImplTest {

    private val authRemoteDataSource: AuthRemoteDataSource = mockk()
    private val repository = AuthRepositoryImpl(authRemoteDataSource)

    private val successLogin = GenericResponse(
        status = 200,
        data = LoginRegisterResponse(
            id = 1L,
            tokens = TokenData(authToken = "a", refreshToken = "r")
        )
    )

    @Test
    fun `login returns success with data`() = runTest {
        val request = LoginRequest("u@test.com", "p")
        coEvery { authRemoteDataSource.login(request) } returns Response.success(successLogin)

        val result = repository.login(request)

        assertTrue(result.isSuccess())
        assertEquals("a", result.data?.tokens?.authToken)
    }

    @Test
    fun `login returns error on HTTP error`() = runTest {
        val request = LoginRequest("u@test.com", "p")
        coEvery { authRemoteDataSource.login(request) } returns Response.error(
            401,
            "Unauthorized".toResponseBody()
        )

        val result = repository.login(request)

        assertFalse(result.isSuccess())
        assertEquals(401, result.status)
    }

    @Test
    fun `login returns INTERNET_ISSUE on UnknownHostException`() = runTest {
        coEvery { authRemoteDataSource.login(any()) } throws UnknownHostException()

        val result = repository.login(LoginRequest("a", "b"))

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }

    @Test
    fun `login returns SLOW_INTERNET on SocketTimeoutException`() = runTest {
        coEvery { authRemoteDataSource.login(any()) } throws SocketTimeoutException()

        val result = repository.login(LoginRequest("a", "b"))

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.SLOW_INTERNET, result.errors?.first()?.code)
    }

    @Test
    fun `refresh returns success with data`() = runTest {
        coEvery { authRemoteDataSource.refresh() } returns Response.success(successLogin)

        val result = repository.refresh()

        assertTrue(result.isSuccess())
        assertEquals("a", result.data?.tokens?.authToken)
    }

    @Test
    fun `register returns success with data`() = runTest {
        coEvery {
            authRemoteDataSource.register(any(), any(), any(), any())
        } returns Response.success(successLogin)

        val result = repository.register(
            "n".toRequestBody(),
            "e".toRequestBody(),
            "p".toRequestBody(),
            null
        )

        assertTrue(result.isSuccess())
        assertEquals(1L, result.data?.id)
    }
}
