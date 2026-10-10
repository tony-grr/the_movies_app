package com.example.themoviesapp.domain.usecase.main

import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.MoviesPage
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        settingsRepository = mockk()
        useCase = GetPopularMoviesUseCase(movieRepository, settingsRepository)
    }

    @Test
    fun `invoke should return MoviesPage on success`() = runTest {
        // Arrange
        val expectedLanguage = "en-US"
        val expectedPage = 1
        val mockMoviesPage = MoviesPage(
            movies = emptyList(),
            currentPage = expectedPage,
            totalPages = 1
        )
        
        io.mockk.every { settingsRepository.getLanguage() } returns flowOf(expectedLanguage)
        coEvery { movieRepository.getPopularMovies(expectedPage, expectedLanguage, null) } returns Result.Success(mockMoviesPage)

        // Act
        val result = useCase(expectedPage)

        // Assert
        assert(result is Result.Success)
        assertEquals(mockMoviesPage, (result as Result.Success).data)
        
        io.mockk.verify(exactly = 1) { settingsRepository.getLanguage() }
        coVerify(exactly = 1) { movieRepository.getPopularMovies(expectedPage, expectedLanguage, null) }
    }

    @Test
    fun `invoke should return Error on failure`() = runTest {
        // Arrange
        val expectedLanguage = "ru-RU"
        val expectedPage = 2
        val expectedError = "Network error"
        
        io.mockk.every { settingsRepository.getLanguage() } returns flowOf(expectedLanguage)
        coEvery { movieRepository.getPopularMovies(expectedPage, expectedLanguage, null) } returns Result.Error(expectedError)

        // Act
        val result = useCase(expectedPage)

        // Assert
        assert(result is Result.Error)
        assertEquals(expectedError, (result as Result.Error).message)
        
        io.mockk.verify(exactly = 1) { settingsRepository.getLanguage() }
        coVerify(exactly = 1) { movieRepository.getPopularMovies(expectedPage, expectedLanguage, null) }
    }
}
