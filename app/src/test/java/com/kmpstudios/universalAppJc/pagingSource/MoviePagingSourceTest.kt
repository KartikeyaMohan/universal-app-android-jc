package com.kmpstudios.universalAppJc.pagingSource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kmpstudios.universalAppJc.data.models.generic.ErrorData
import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.data.utils.ErrorTypes
import com.kmpstudios.universalAppJc.data.utils.StatusCodes
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase
import com.kmpstudios.universalAppJc.ui.pagingSource.MoviePagingSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MoviePagingSourceTest {

    private val getMoviesUseCase: GetMoviesUseCase = mockk()
    private val pagingSource = MoviePagingSource(getMoviesUseCase)

    private val fakeMovies = listOf(
        MovieData(1L, "Tenet", "", 4.0f),
        MovieData(2L, "Inception", "", 4.2f),
    )

    @Test
    fun `load returns page on success`() = runTest {
        coEvery { getMoviesUseCase.execute(1, 20) } returns GenericResponse(
            status = 200,
            data = MovieResponse(
                list = fakeMovies,
                meta = PagerMetaData(prevPage = null, nextPage = 2)
            )
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = fakeMovies,
                prevKey = null,
                nextKey = 2
            ),
            result
        )
    }

    @Test
    fun `load returns error on exception`() = runTest {
        coEvery {
            getMoviesUseCase.execute(any(), any())
        } throws IOException("Network error")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals("Network error", (result as PagingSource.LoadResult.Error).throwable.message)
    }

    @Test
    fun `load returns null nextKey when list is empty`() = runTest {
        coEvery { getMoviesUseCase.execute(any(), any()) } returns GenericResponse(
            status = 200,
            data = MovieResponse(list = emptyList(), meta = PagerMetaData(null, null))
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 20, placeholdersEnabled = false)
        )

        val page = result as PagingSource.LoadResult.Page
        assertNull(page.nextKey)
    }

    @Test
    fun `getRefreshKey returns correct key`() {
        val state = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = fakeMovies,
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(state)

        assertEquals(1, refreshKey)
    }

    @Test
    fun `load returns error when response is not successful`() = runTest {
        coEvery { getMoviesUseCase.execute(any(), any()) } returns GenericResponse(
            status = 500,
            data = null,
            errors = listOf(ErrorData(ErrorTypes.DEFAULT_ERROR, "Something went wrong"))
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `load returns error on internet issue response`() = runTest {
        coEvery { getMoviesUseCase.execute(any(), any()) } returns GenericResponse(
            status = StatusCodes.REQUEST_TIMEOUT,
            data = null,
            errors = listOf(ErrorData(ErrorTypes.INTERNET_ISSUE, "Internet connectivity issue"))
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(
            "Internet connectivity issue",
            (result as PagingSource.LoadResult.Error).throwable.message
        )
    }
}