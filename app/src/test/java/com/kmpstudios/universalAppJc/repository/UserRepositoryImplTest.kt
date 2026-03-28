package com.kmpstudios.universalAppJc.repository

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.repository.UserRepositoryImpl
import com.kmpstudios.universalAppJc.data.repository.dataSource.remote.UserRemoteDataSource
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
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val userRemoteDataSource: UserRemoteDataSource = mockk()
    private val repository = UserRepositoryImpl(userRemoteDataSource)

    private val profileOk = GenericResponse(
        status = 200,
        data = ProfileResponse(name = "User", email = "u@test.com", profileImageUrl = null)
    )

    @Test
    fun `getProfile returns success with data`() = runTest {
        coEvery { userRemoteDataSource.getProfile() } returns Response.success(profileOk)

        val result = repository.getProfile()

        assertTrue(result.isSuccess())
        assertEquals("User", result.data?.name)
    }

    @Test
    fun `getProfile returns error on HTTP error`() = runTest {
        coEvery { userRemoteDataSource.getProfile() } returns Response.error(
            500,
            "Error".toResponseBody()
        )

        val result = repository.getProfile()

        assertFalse(result.isSuccess())
        assertEquals(500, result.status)
    }

    @Test
    fun `getProfile returns INTERNET_ISSUE on UnknownHostException`() = runTest {
        coEvery { userRemoteDataSource.getProfile() } throws UnknownHostException()

        val result = repository.getProfile()

        assertEquals(StatusCodes.REQUEST_TIMEOUT, result.status)
        assertEquals(ErrorTypes.INTERNET_ISSUE, result.errors?.first()?.code)
    }
}
