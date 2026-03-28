package com.kmpstudios.universalAppJc.viewmodels

import com.kmpstudios.universalAppJc.data.models.generic.ErrorData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.Cast
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMovieDetailsUseCase
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieDetailsViewModel
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()

    private val fakeMovieDetails = MovieDetailsResponse(
        id = 1L,
        name = "Tenet",
        description = "",
        rating = 4.0F,
        trailerUrl = "",
        casts = listOf(Cast(id = 1L, name = "Cristopher Nolan", imageUrl = "", castType = "Director"))
    )

    private fun createViewModel() = MovieDetailsViewModel(
        getMovieDetailsUseCase = getMovieDetailsUseCase,
        movieId = 1L
    )

    @Test
    fun `initial state is null before coroutine completes`() = runTest(testDispatcher) {
        coEvery { getMovieDetailsUseCase.execute(1L) } coAnswers {
            delay(1000L)
            GenericResponse(status = 200, data = fakeMovieDetails)
        }

        val viewModel = createViewModel()

        assertNull(viewModel.movieDetailResponse.value)
        advanceUntilIdle()
        assertNotNull(viewModel.movieDetailResponse.value)
    }


    @Test
    fun `state is populated after successful fetch`() = runTest(testDispatcher) {
        coEvery {
            getMovieDetailsUseCase.execute(1L)
        } returns GenericResponse(status = 200, data = fakeMovieDetails)

        val viewModel = createViewModel()
        advanceUntilIdle()
        assertEquals(fakeMovieDetails, viewModel.movieDetailResponse.value)
    }

    @Test
    fun `state remains null on error response`() = runTest(testDispatcher) {
        coEvery {
            getMovieDetailsUseCase.execute(1L)
        } returns GenericResponse(status = 404, data = null)

        val viewModel = createViewModel()
        advanceUntilIdle()
        assertNull(viewModel.movieDetailResponse.value)
    }

    @Test
    fun `useCase is called exactly once on init`() = runTest(testDispatcher) {
        coEvery {
            getMovieDetailsUseCase.execute(1L)
        } returns GenericResponse(status = 200, data = fakeMovieDetails)

        createViewModel()
        advanceUntilIdle()
        coVerify(exactly = 1) { getMovieDetailsUseCase.execute(1L) }
    }
}