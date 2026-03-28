package com.kmpstudios.universalAppJc.viewmodels

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.users.ProfileResponse
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import com.kmpstudios.universalAppJc.domain.useCases.auth.RefreshTokenUseCase
import com.kmpstudios.universalAppJc.domain.useCases.users.GetUserProfileUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import com.kmpstudios.universalAppJc.ui.viewmodels.MainViewModel
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val tokenManager: TokenManager = mockk(relaxed = true)
    private val getUserProfileUseCase: GetUserProfileUseCase = mockk()
    private val refreshTokenUseCase: RefreshTokenUseCase = mockk()

    @Test
    fun `empty auth token sets unauthenticated and ready without profile call`() = runTest(testDispatcher) {
        every { tokenManager.getAuthToken() } returns ""

        val vm = MainViewModel(tokenManager, getUserProfileUseCase, refreshTokenUseCase)
        advanceUntilIdle()

        assertEquals(false, vm.isAuthenticated.value)
        assertEquals(true, vm.isReady.value)
        coVerify(exactly = 0) { getUserProfileUseCase.execute() }
    }

    @Test
    fun `valid token and profile success sets authenticated`() = runTest(testDispatcher) {
        every { tokenManager.getAuthToken() } returns "tok"
        coEvery { getUserProfileUseCase.execute() } returns GenericResponse(
            status = 200,
            data = ProfileResponse("U", "u@test.com", null)
        )

        val vm = MainViewModel(tokenManager, getUserProfileUseCase, refreshTokenUseCase)
        advanceUntilIdle()

        assertEquals(true, vm.isAuthenticated.value)
        assertEquals(true, vm.isReady.value)
    }

    @Test
    fun `unauthorized profile clears auth and refresh success saves tokens`() = runTest(testDispatcher) {
        every { tokenManager.getAuthToken() } returns "old"
        coEvery { getUserProfileUseCase.execute() } returns GenericResponse(
            status = StatusCodes.UNAUTHORIZED,
            data = null
        )
        every { tokenManager.clearAuthToken() } returns Unit
        coEvery { refreshTokenUseCase.execute() } returns GenericResponse(
            status = 200,
            data = LoginRegisterResponse(tokens = TokenData("na", "nr"))
        )
        every { tokenManager.saveTokens(any(), any()) } returns Unit

        val vm = MainViewModel(tokenManager, getUserProfileUseCase, refreshTokenUseCase)
        advanceUntilIdle()

        assertEquals(true, vm.isAuthenticated.value)
        assertEquals(true, vm.isReady.value)
        verify { tokenManager.saveTokens("na", "nr") }
    }

    @Test
    fun `unauthorized profile and failed refresh clears all tokens`() = runTest(testDispatcher) {
        every { tokenManager.getAuthToken() } returns "old"
        coEvery { getUserProfileUseCase.execute() } returns GenericResponse(
            status = StatusCodes.UNAUTHORIZED,
            data = null
        )
        every { tokenManager.clearAuthToken() } returns Unit
        coEvery { refreshTokenUseCase.execute() } returns GenericResponse(
            status = 400,
            data = null
        )
        every { tokenManager.clearTokens() } returns Unit

        val vm = MainViewModel(tokenManager, getUserProfileUseCase, refreshTokenUseCase)
        advanceUntilIdle()

        assertEquals(false, vm.isAuthenticated.value)
        assertEquals(true, vm.isReady.value)
        verify { tokenManager.clearTokens() }
    }

    @Test
    fun `non success non unauthorized profile sets unauthenticated`() = runTest(testDispatcher) {
        every { tokenManager.getAuthToken() } returns "tok"
        coEvery { getUserProfileUseCase.execute() } returns GenericResponse(
            status = 500,
            data = null
        )

        val vm = MainViewModel(tokenManager, getUserProfileUseCase, refreshTokenUseCase)
        advanceUntilIdle()

        assertEquals(false, vm.isAuthenticated.value)
        assertEquals(true, vm.isReady.value)
    }
}
