package com.example.themoviesapp.presentation.screen.main.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviesapp.domain.entity.movie.Movie
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieCardDisplaysDetailsAndHandlesClicks() {
        val movie = Movie(
            id = 1,
            title = "Test Movie Card",
            originalTitle = "Original",
            overview = "Overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2024",
            voteAverage = 9.9,
            voteCount = 100,
            popularity = 1.0,
            genreIds = emptyList(),
            adult = false,
            video = false,
            originalLanguage = "en"
        )
        val genreNames = listOf("Action", "Drama")
        
        var movieClickedId = -1
        var favoriteClickedMovie: Movie? = null
        var favIconContentDescription = ""
        var ratingText = ""

        composeTestRule.setContent {
            favIconContentDescription = "Favorite"
            ratingText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.movie_rating_format, movie.voteAverage)
            
            MovieCard(
                movie = movie,
                genreNames = genreNames,
                isFavorite = false,
                onMovieClick = { movieClickedId = it },
                onFavoriteClick = { favoriteClickedMovie = it }
            )
        }

        // Verify title, rating, and genres
        composeTestRule.onNodeWithText("Test Movie Card").assertIsDisplayed()
        composeTestRule.onNodeWithText(ratingText).assertIsDisplayed()
        composeTestRule.onNodeWithText("Action, Drama").assertIsDisplayed()

        // Verify clicks
        composeTestRule.onNodeWithText("Test Movie Card").performClick()
        assertTrue(movieClickedId == 1)

        composeTestRule.onNodeWithContentDescription(favIconContentDescription).performClick()
        assertTrue(favoriteClickedMovie == movie)
    }
}
