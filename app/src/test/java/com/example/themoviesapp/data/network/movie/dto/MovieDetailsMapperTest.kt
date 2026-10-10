package com.example.themoviesapp.data.network.movie.dto

import com.example.themoviesapp.domain.entity.movie.Genre
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsMapperTest {

    @Test
    fun `toDomain correctly maps MovieDetailsDto to MovieDetails`() {
        // Arrange
        val dto = MovieDetailsDto(
            adult = false,
            backdropPath = "/backdrop.jpg",
            budget = 1000000,
            genres = listOf(
                GenreDto(id = 1, name = "Action"),
                GenreDto(id = 2, name = "Comedy")
            ),
            homepage = "https://example.com",
            id = 123,
            imdbId = "tt1234567",
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Test overview",
            popularity = 100.5,
            posterPath = "/poster.jpg",
            releaseDate = "2024-01-01",
            revenue = 5000000,
            runtime = 120,
            status = "Released",
            tagline = "Test tagline",
            title = "Test Title",
            video = false,
            voteAverage = 8.5,
            voteCount = 1000
        )

        // Act
        val domain = dto.toDomain()

        // Assert
        assertEquals(dto.adult, domain.adult)
        assertEquals(dto.backdropPath, domain.backdropPath)
        assertEquals(dto.budget, domain.budget)
        
        assertEquals(2, domain.genres.size)
        assertEquals(Genre(1, "Action"), domain.genres[0])
        assertEquals(Genre(2, "Comedy"), domain.genres[1])
        
        assertEquals(dto.homepage, domain.homepage)
        assertEquals(dto.id, domain.id)
        assertEquals(dto.imdbId, domain.imdbId)
        assertEquals(dto.originalLanguage, domain.originalLanguage)
        assertEquals(dto.originalTitle, domain.originalTitle)
        assertEquals(dto.overview, domain.overview)
        assertEquals(dto.popularity, domain.popularity, 0.001)
        assertEquals(dto.posterPath, domain.posterPath)
        assertEquals(dto.releaseDate, domain.releaseDate)
        assertEquals(dto.revenue, domain.revenue)
        assertEquals(dto.runtime, domain.runtime)
        assertEquals(dto.status, domain.status)
        assertEquals(dto.tagline, domain.tagline)
        assertEquals(dto.title, domain.title)
        assertEquals(dto.video, domain.video)
        assertEquals(dto.voteAverage, domain.voteAverage, 0.001)
        assertEquals(dto.voteCount, domain.voteCount)
    }
}
