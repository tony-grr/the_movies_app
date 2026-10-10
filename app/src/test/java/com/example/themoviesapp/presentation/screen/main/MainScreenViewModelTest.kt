package com.example.themoviesapp.presentation.screen.main

import com.example.themoviesapp.MainDispatcherRule
import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.genre.Genre
import com.example.themoviesapp.domain.entity.movie.MoviesPage
import com.example.themoviesapp.domain.usecase.favorite.ObserveFavoriteIdsUseCase
import com.example.themoviesapp.domain.usecase.favorite.ToggleFavoriteUseCase
import com.example.themoviesapp.domain.usecase.main.GetGenresUseCase
import com.example.themoviesapp.domain.usecase.main.GetPopularMoviesUseCase
import com.example.themoviesapp.domain.usecase.main.GetTopRatedMoviesUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var observeFavoriteIdsUseCase: ObserveFavoriteIdsUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setup() {
        getPopularMoviesUseCase = mockk()
        getTopRatedMoviesUseCase = mockk()
        getGenresUseCase = mockk()
        observeFavoriteIdsUseCase = mockk()
        toggleFavoriteUseCase = mockk()

        // Default mock responses for init blocks
        coEvery { getGenresUseCase() } returns flowOf(listOf(Genre(1, "Action")))
        every { observeFavoriteIdsUseCase() } returns flowOf(listOf(1, 2, 3))
        coEvery { getPopularMoviesUseCase(any(), any()) } returns Result.Success(MoviesPage(emptyList(), 1, 1))

        viewModel = MainScreenViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getGenresUseCase,
            observeFavoriteIdsUseCase,
            toggleFavoriteUseCase
        )
    }

    @Test
    fun `initialization loads genres, favorites and first page of popular movies`() = runTest {
        advanceUntilIdle()
        val state = viewModel.uiState.value
        
        assertEquals(false, state.isLoading)
        assertEquals(1, state.currentPage)
        assertEquals(1, state.totalPages)
        assertEquals(setOf(1, 2, 3), state.favoriteIds)
        assertEquals(mapOf(1 to "Action"), state.genresMap)
        assertEquals(MoviesSort.ByPopular, state.sort)
    }

    @Test
    fun `ChangeSort intent triggers reset and reload of movies`() = runTest {
        val mockTopRatedPage = MoviesPage(emptyList(), 1, 5)
        coEvery { getTopRatedMoviesUseCase(1, any()) } returns Result.Success(mockTopRatedPage)

        viewModel.handleIntent(MainScreenIntents.ChangeSort(MoviesSort.ByTopRated))
        
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(MoviesSort.ByTopRated, state.sort)
        assertEquals(1, state.currentPage)
        assertEquals(5, state.totalPages)
    }
}
