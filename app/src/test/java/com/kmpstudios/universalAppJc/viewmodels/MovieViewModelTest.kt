package com.kmpstudios.universalAppJc.viewmodels

import androidx.paging.AsyncPagingDataDiffer
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieViewModel
import com.kmpstudios.universalAppJc.utils.MainDispatcherRule
import com.kmpstudios.universalAppJc.utils.MovieDiffCallback
import com.kmpstudios.universalAppJc.utils.NoopListCallback
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher = testDispatcher)

    private val getMoviesUseCase: GetMoviesUseCase = mockk()

    private val fakeMovies = listOf(
        MovieData(1L, "Tenet", "", 4.0f),
        MovieData(2L, "Inception", "", 4.2f),
        MovieData(3L, "Interstellar", "", 4.5f),
    )

    @Test
    fun `moviesPager emits PagingData with correct items`() = runTest(testDispatcher) {
        coEvery { getMoviesUseCase.execute(any(), any()) } returns GenericResponse(
            status = 200,
            data = MovieResponse(
                list = fakeMovies,
                meta = PagerMetaData(prevPage = null, nextPage = null)
            )
        )

        val viewModel = MovieViewModel(getMoviesUseCase)

        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieDiffCallback(),
            updateCallback = NoopListCallback(),
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        val job = launch {
            viewModel.moviesPager.collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }
        advanceUntilIdle()
        assertEquals(fakeMovies, differ.snapshot().items)
        job.cancel()
    }

    @Test
    fun `moviesPager emits empty list when no movies returned`() = runTest(testDispatcher) {
        coEvery { getMoviesUseCase.execute(any(), any()) } returns GenericResponse(
            status = 200,
            data = MovieResponse(list = emptyList(), meta = PagerMetaData(null, null))
        )

        val viewModel = MovieViewModel(getMoviesUseCase)

        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieDiffCallback(),
            updateCallback = NoopListCallback(),
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        val job = launch {
            viewModel.moviesPager.collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        advanceUntilIdle()
        assertTrue(differ.snapshot().items.isEmpty())
        job.cancel()
    }

    @Test
    fun `moviesPager uses correct page size config`() = runTest(testDispatcher) {
        val limitSlot = slot<Int>()
        val pageSlot = slot<Int>()

        coEvery {
            getMoviesUseCase.execute(capture(pageSlot), capture(limitSlot))
        } returns GenericResponse(
            status = 200,
            data = MovieResponse(list = fakeMovies, meta = PagerMetaData(null, null))
        )

        val viewModel = MovieViewModel(getMoviesUseCase)

        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieDiffCallback(),
            updateCallback = NoopListCallback(),
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        val job = launch {
            viewModel.moviesPager.collectLatest { pagingData ->
                differ.submitData(pagingData)  // ← submitData triggers the actual load
            }
        }

        advanceUntilIdle()
        assertEquals(fakeMovies, differ.snapshot().items)
        assertEquals(10, limitSlot.captured)
        assertEquals(1, pageSlot.captured)

        job.cancel()
    }
}