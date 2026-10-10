package com.example.themoviesapp.data.network.movie

import com.example.themoviesapp.data.network.movie.dto.MovieDetailsDto
import com.example.themoviesapp.data.network.movie.dto.MovieDto
import com.example.themoviesapp.data.network.movie.dto.MovieResponseDto
import com.example.themoviesapp.data.network.movie.service.MovieApiService
import com.example.themoviesapp.domain.common.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    private lateinit var movieApiService: MovieApiService
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        movieApiService = mockk()
        repository = MovieRepositoryImpl(movieApiService)
    }

    @Test
    fun `getPopularMovies returns Success with mapped data`() = runTest {
        // Arrange
        val dto = MovieDto(
            id = 1,
            title = "Test",
            overview = "Overview",
            posterPath = null,
            backdropPath = null,
            voteAverage = 5.0,
            releaseDate = "2024",
            popularity = 1.0,
            genreIds = emptyList(),
            adult = false,
            video = false,
            originalLanguage = "en",
            originalTitle = "Test",
            voteCount = 10
        )
        val response = MovieResponseDto(page = 1, results = listOf(dto), totalPages = 10, totalResults = 100)
        coEvery { movieApiService.getPopularMovies("en", 1, null) } returns response

        // Act
        val result = repository.getPopularMovies(1, "en", null)

        // Assert
        assert(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.currentPage)
        assertEquals(10, data.totalPages)
        assertEquals(1, data.movies.size)
        assertEquals(dto.id, data.movies[0].id)
    }

    @Test
    fun `getPopularMovies catches exception and returns Error`() = runTest {
        // Arrange
        val errorMessage = "Network timeout"
        coEvery { movieApiService.getPopularMovies(any(), any(), any()) } throws Exception(errorMessage)

        // Act
        val result = repository.getPopularMovies(1, "en", null)

        // Assert
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    @Test
    fun `getMovieDetails returns Success with mapped data`() = runTest {
        // Arrange
        val dto = MovieDetailsDto(
            adult = false,
            backdropPath = null,
            budget = 0,
            genres = emptyList(),
            homepage = "",
            id = 1,
            imdbId = "",
            originalLanguage = "en",
            originalTitle = "Title",
            overview = "Overview",
            popularity = 0.0,
            posterPath = null,
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            status = "",
            tagline = "",
            title = "Title",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )
        coEvery { movieApiService.getMovieDetails(1, "en") } returns dto

        // Act
        val result = repository.getMovieDetails(1, "en")

        // Assert
        assert(result is Result.Success)
        assertEquals(dto.id, (result as Result.Success).data.id)
    }

    @Test
    fun `getMovieDetails catches exception and returns Error`() = runTest {
        // Arrange
        val errorMessage = "Not found"
        coEvery { movieApiService.getMovieDetails(any(), any()) } throws Exception(errorMessage)

        // Act
        val result = repository.getMovieDetails(1, "en")

        // Assert
        assert(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }
}
