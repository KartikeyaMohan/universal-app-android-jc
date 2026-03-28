package com.kmpstudios.universalAppJc.useCases.movies

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.movies.MovieDetailsResponse
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMoviesDetailUseCaseTest {

    private val movieRepository: MovieRepository = mockk()
    private val useCase = GetMovieDetailsUseCase(movieRepository)

    @Test
    fun `execute calls repository with correct id`() = runTest {
        val expected = GenericResponse(
            status = 200,
            data = MovieDetailsResponse(
                1L,
                "Tenet",
                "",
                emptyList(),
                "",
                4.0F,
                emptyList(),
                "A thriller"
            )
        )
        coEvery {
            movieRepository.getMovieDetails(1L)
        } returns expected

        val result = useCase.execute(1L)

        assertEquals(expected, result)
        coVerify(exactly = 1) {
            movieRepository.getMovieDetails(1L)
        }
    }

    @Test
    fun `execute returns error response from repository`() = runTest {
        val errorResponse = GenericResponse<MovieDetailsResponse>(status = 404, data = null)
        coEvery { movieRepository.getMovieDetails(99L) } returns errorResponse

        val result = useCase.execute(99L)

        assertFalse(result.isSuccess())
        assertNull(result.data)
    }
}