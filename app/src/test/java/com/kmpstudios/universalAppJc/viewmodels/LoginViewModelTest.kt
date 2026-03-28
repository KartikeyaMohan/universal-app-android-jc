package com.kmpstudios.universalAppJc.viewmodels

import com.kmpstudios.universalAppJc.data.models.auth.LoginRegisterResponse
import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.auth.TokenData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.domain.useCases.auth.LoginUserUseCase
import com.kmpstudios.universalAppJc.ui.utils.TokenManager
import com.kmpstudios.universalAppJc.ui.viewmodels.LoginViewModel
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import io.mockk.coEvery
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
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val tokenManager: TokenManager = mockk(relaxed = true)
    private val loginUserUseCase: LoginUserUseCase = mockk()

    @Test
    fun `login success with tokens saves tokens and exposes response`() = runTest(testDispatcher) {
        val data = LoginRegisterResponse(
            tokens = TokenData(authToken = "auth", refreshToken = "ref")
        )
        coEvery { loginUserUseCase.execute(any()) } returns GenericResponse(status = 200, data = data)

        val viewModel = LoginViewModel(tokenManager, loginUserUseCase)
        viewModel.login("a@test.com", "pw")
        advanceUntilIdle()

        assertEquals(data, viewModel.loginResponse.value)
        verify(exactly = 1) { tokenManager.saveTokens("auth", "ref") }
    }

    @Test
    fun `login failure does not update response`() = runTest(testDispatcher) {
        coEvery {
            loginUserUseCase.execute(any())
        } returns GenericResponse(status = 400, data = null)

        val viewModel = LoginViewModel(tokenManager, loginUserUseCase)
        viewModel.login("a@test.com", "pw")
        advanceUntilIdle()

        assertNull(viewModel.loginResponse.value)
        verify(exactly = 0) { tokenManager.saveTokens(any(), any()) }
    }

    @Test
    fun `login passes credentials to use case`() = runTest(testDispatcher) {
        val slot = io.mockk.slot<LoginRequest>()
        coEvery { loginUserUseCase.execute(capture(slot)) } returns GenericResponse(
            status = 200,
            data = LoginRegisterResponse(tokens = TokenData("a", "b"))
        )

        val viewModel = LoginViewModel(tokenManager, loginUserUseCase)
        viewModel.login("user@test.com", "secret")
        advanceUntilIdle()

        assertEquals("user@test.com", slot.captured.email)
        assertEquals("secret", slot.captured.password)
    }
}
