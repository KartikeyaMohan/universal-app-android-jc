package com.kmpstudios.universalAppJc.useCases.users

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.domain.repository.UserRepository
import com.kmpstudios.universalAppJc.domain.useCases.users.GetUserProfileUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetUserProfileUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private val useCase = GetUserProfileUseCase(userRepository)

    @Test
    fun `execute delegates to repository`() = runTest {
        val expected = GenericResponse(
            status = 200,
            data = ProfileResponse("Me", "me@test.com", null)
        )
        coEvery { userRepository.getProfile() } returns expected

        val result = useCase.execute()

        assertEquals(expected, result)
        coVerify(exactly = 1) { userRepository.getProfile() }
    }
}
