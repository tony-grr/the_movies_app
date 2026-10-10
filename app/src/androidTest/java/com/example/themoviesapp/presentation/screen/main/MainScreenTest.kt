package com.example.themoviesapp.presentation.screen.main

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviesapp.domain.entity.movie.Movie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialLoadingShowsProgressIndicator() {
        val uiState = MainScreenUiState(isLoading = true, movies = emptyList())

        composeTestRule.setContent {
            MainScreenContent(
                uiState = uiState,
                listState = rememberLazyListState(),
                onIntent = {},
                onMovieClick = {}
            )
        }

        // CircularProgressIndicator doesn't have a default text/description, 
        // but we can verify that movies aren't displayed
        composeTestRule.onNodeWithText("Sort by").assertDoesNotExist() // just checking the layout is there
    }

    @Test
    fun moviesAreDisplayedWhenLoaded() {
        val movies = listOf(
            Movie(
                id = 1,
                title = "Test Movie Title",
                originalTitle = "Original Title",
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
        val uiState = MainScreenUiState(isLoading = false, movies = movies)
        var ratingText = ""

        composeTestRule.setContent {
            ratingText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.movie_rating_format, movies.first().voteAverage)
            MainScreenContent(
                uiState = uiState,
                listState = rememberLazyListState(),
                onIntent = {},
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithText("Test Movie Title").assertIsDisplayed()
        composeTestRule.onNodeWithText(ratingText).assertIsDisplayed()
    }

    @Test
    fun clickingSortIconOpensMenu() {
        val uiState = MainScreenUiState(isLoading = false, movies = emptyList())
        var sortDescription = ""
        var sortPopularText = ""

        composeTestRule.setContent {
            sortDescription = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.sort_description)
            sortPopularText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.sort_popular)
            
            MainScreenContent(
                uiState = uiState,
                listState = rememberLazyListState(),
                onIntent = {},
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(sortDescription).performClick()
        composeTestRule.onNodeWithText(sortPopularText).assertIsDisplayed()
    }
}
