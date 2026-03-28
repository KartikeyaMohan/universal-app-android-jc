package com.kmpstudios.universalAppJc.viewmodels

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.useCases.auth.RegisterUserUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import com.kmpstudios.universalAppJc.ui.viewmodels.RegisterViewModel
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val tokenManager: TokenManager = mockk(relaxed = true)
    private val registerUserUseCase: RegisterUserUseCase = mockk()

    @Test
    fun `register success with tokens saves tokens and exposes response`() = runTest(testDispatcher) {
        val data = LoginRegisterResponse(
            id = 1L,
            tokens = TokenData(authToken = "a", refreshToken = "r")
        )
        coEvery {
            registerUserUseCase.execute(any(), any(), any(), any())
        } returns GenericResponse(status = 200, data = data)

        val viewModel = RegisterViewModel(tokenManager, registerUserUseCase)
        viewModel.register("N", "e@test.com", "pw", null)
        advanceUntilIdle()

        assertEquals(data, viewModel.registerResponse.value)
        verify(exactly = 1) { tokenManager.saveTokens("a", "r") }
    }

    @Test
    fun `register failure does not update response`() = runTest(testDispatcher) {
        coEvery {
            registerUserUseCase.execute(any(), any(), any(), any())
        } returns GenericResponse(status = 422, data = null)

        val viewModel = RegisterViewModel(tokenManager, registerUserUseCase)
        viewModel.register("N", "e@test.com", "pw", null)
        advanceUntilIdle()

        assertNull(viewModel.registerResponse.value)
        verify(exactly = 0) { tokenManager.saveTokens(any(), any()) }
    }

    @Test
    fun `register forwards arguments to use case`() = runTest(testDispatcher) {
        coEvery {
            registerUserUseCase.execute("Name", "e@x.com", "secret", null)
        } returns GenericResponse(
            status = 200,
            data = LoginRegisterResponse(tokens = TokenData("x", "y"))
        )

        val viewModel = RegisterViewModel(tokenManager, registerUserUseCase)
        viewModel.register("Name", "e@x.com", "secret", null)
        advanceUntilIdle()

        coVerify(exactly = 1) {
            registerUserUseCase.execute("Name", "e@x.com", "secret", null)
        }
    }
}
