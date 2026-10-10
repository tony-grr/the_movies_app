package com.example.themoviesapp.domain.usecase.favorite

import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var useCase: ToggleFavoriteUseCase

    @Before
    fun setup() {
        favoritesRepository = mockk()
        useCase = ToggleFavoriteUseCase(favoritesRepository)
    }

    @Test
    fun `invoke should call toggleFavorite on repository`() = runTest {
        // Arrange
        val mockMovie = Movie(
            id = 1,
            title = "Test Movie",
            originalTitle = "Original Test Movie",
            overview = "Test Overview",
            posterPath = null,
            backdropPath = null,
            voteAverage = 8.0,
            voteCount = 100,
            releaseDate = "2024-01-01",
            popularity = 100.0,
            genreIds = emptyList(),
            adult = false,
            video = false,
            originalLanguage = "en"
        )
        
        coEvery { favoritesRepository.toggleFavorite(mockMovie) } just runs

        // Act
        useCase(mockMovie)

        // Assert
        coVerify(exactly = 1) { favoritesRepository.toggleFavorite(mockMovie) }
    }
}
