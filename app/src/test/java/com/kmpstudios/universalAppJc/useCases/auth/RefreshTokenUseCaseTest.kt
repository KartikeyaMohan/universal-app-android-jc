package com.kmpstudios.universalAppJc.useCases.auth

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.repository.AuthRepository
import com.kmpstudios.universalAppJc.domain.useCases.auth.RefreshTokenUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RefreshTokenUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val useCase = RefreshTokenUseCase(authRepository)

    @Test
    fun `execute delegates to repository`() = runTest {
        val expected = GenericResponse(
            status = 200,
            data = LoginRegisterResponse(tokens = TokenData("na", "nr"))
        )
        coEvery { authRepository.refresh() } returns expected

        val result = useCase.execute()

        assertEquals(expected, result)
        coVerify(exactly = 1) { authRepository.refresh() }
    }
}
