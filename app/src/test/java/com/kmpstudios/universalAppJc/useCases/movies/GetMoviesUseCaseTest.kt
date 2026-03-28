package com.kmpstudios.universalAppJc.useCases.movies

import com.kmpstudios.universalAppJc.data.models.generic.GenericResponse
import com.kmpstudios.universalAppJc.data.models.generic.PagerMetaData
import com.kmpstudios.universalAppJc.data.models.movies.MovieData
import com.kmpstudios.universalAppJc.data.models.movies.MovieResponse
import com.kmpstudios.universalAppJc.domain.repository.MovieRepository
import com.kmpstudios.universalAppJc.domain.useCases.movies.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMoviesUseCaseTest {
    private val movieRepository: MovieRepository = mockk()
    private val useCase = GetMoviesUseCase(movieRepository)

    @Test
    fun `execute calls repository with correct page and limit`() = runTest {
        val expected = GenericResponse(
            status = 200,
            data = MovieResponse(
                list = listOf(MovieData(1L, "Tenet", "", 4.0f)),
                meta = PagerMetaData(null, 2)
            )
        )

        coEvery { movieRepository.getMovies(1, 10) } returns expected

        val result = useCase.execute(1, 10)

        assertEquals(expected, result)
        coVerify(exactly = 1) { movieRepository.getMovies(1, 10) }
    }
}