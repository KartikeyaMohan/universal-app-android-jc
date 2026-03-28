package com.kmpstudios.universalAppJc.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.useCases.auth.LoginUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginUserUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val useCase = LoginUserUseCase(authRepository)

    @Test
    fun `execute delegates to repository`() = runTest {
        val request = LoginRequest("a@b.com", "secret")
        val expected = GenericResponse(
            status = 200,
            data = LoginRegisterResponse(tokens = TokenData("x", "y"))
        )
        coEvery { authRepository.login(request) } returns expected

        val result = useCase.execute(request)

        assertEquals(expected, result)
        coVerify(exactly = 1) { authRepository.login(request) }
    }
}
