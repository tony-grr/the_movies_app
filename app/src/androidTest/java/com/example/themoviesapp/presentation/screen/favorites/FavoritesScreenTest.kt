package com.example.themoviesapp.presentation.screen.favorites

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviesapp.domain.entity.movie.Movie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyListMessageIsDisplayedWhenNoMovies() {
        val uiState = FavoritesUiState(isLoading = false, movies = emptyList())
        var emptyText = ""

        composeTestRule.setContent {
            emptyText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.favorites_empty_list)
            FavoritesScreenContent(
                uiState = uiState,
                onMovieClick = {},
                onFavoriteClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithText(emptyText).assertIsDisplayed()
    }

    @Test
    fun moviesAreDisplayedWhenListIsNotEmpty() {
        val movies = listOf(
            Movie(
                id = 1,
                title = "Favorite Movie Title",
                originalTitle = "Original",
                overview = "Overview",
                posterPath = null,
                backdropPath = null,
                releaseDate = "2024",
                voteAverage = 9.0,
                voteCount = 100,
                popularity = 1.0,
                genreIds = emptyList(),
                adult = false,
                video = false,
                originalLanguage = "en"
            )
        )
        val uiState = FavoritesUiState(isLoading = false, movies = movies)
        var ratingText = ""

        composeTestRule.setContent {
            ratingText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.movie_rating_format, movies.first().voteAverage)
            FavoritesScreenContent(
                uiState = uiState,
                onMovieClick = {},
                onFavoriteClick = { _ -> }
            )
        }

        composeTestRule.onNodeWithText("Favorite Movie Title").assertIsDisplayed()
        composeTestRule.onNodeWithText(ratingText).assertIsDisplayed()
    }
}
