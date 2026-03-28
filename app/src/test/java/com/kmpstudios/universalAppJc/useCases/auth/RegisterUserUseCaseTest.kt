package com.kmpstudios.universalAppJc.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.useCases.auth.RegisterUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RegisterUserUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val useCase = RegisterUserUseCase(authRepository)

    @Test
    fun `execute delegates to repository`() = runTest {
        val expected = GenericResponse(
            status = 201,
            data = LoginRegisterResponse(id = 3L, tokens = TokenData("a", "r"))
        )
        coEvery {
            authRepository.register(any(), any(), any(), any())
        } returns expected

        val result = useCase.execute("Name", "e@test.com", "pw", null)

        assertEquals(expected, result)
        coVerify(exactly = 1) { authRepository.register(any(), any(), any(), null) }
    }
}
