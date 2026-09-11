package com.example.themoviesapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.domain.usecase.main.GetGenresUseCase
import com.example.themoviesapp.domain.usecase.main.GetPopularMoviesUseCase
import com.example.themoviesapp.domain.usecase.main.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGenres()
        handleIntent(MainScreenIntents.Load)
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { genres ->
                _uiState.update { state ->
                    state.copy(genresMap = genres.associate { it.id to it.name })
                }
            }
        }
    }

    fun handleIntent(intent: MainScreenIntents) {
        when (intent) {
            is MainScreenIntents.Load -> loadMovies()
            is MainScreenIntents.ChangeSort -> {
                if (_uiState.value.sort != intent.sort) {
                    _uiState.update { it.copy(sort = intent.sort, currentPage = 1, movies = emptyList()) }
                    loadMovies()
                }
            }
            is MainScreenIntents.LoadNextPage -> {
                if (!_uiState.value.isLoading && !_uiState.value.isPaginationLoading && _uiState.value.currentPage < _uiState.value.totalPages) {
                    loadMovies(isPagination = true)
                }
            }
        }
    }

    private fun loadMovies(isPagination: Boolean = false) {
        viewModelScope.launch {
            if (isPagination) {
                _uiState.update { it.copy(isPaginationLoading = true) }
            } else {
                _uiState.update { it.copy(isLoading = true, movies = emptyList(), currentPage = 1) }
            }

            try {
                val pageToLoad = if (isPagination) _uiState.value.currentPage + 1 else 1
                val moviesPage = when (_uiState.value.sort) {
                    MoviesSort.ByPopular -> getPopularMoviesUseCase(page = pageToLoad)
                    MoviesSort.ByTopRated -> getTopRatedMoviesUseCase(page = pageToLoad)
                }

                _uiState.update { state ->
                    state.copy(
                        movies = if (isPagination) state.movies + moviesPage.movies else moviesPage.movies,
                        currentPage = moviesPage.currentPage,
                        totalPages = moviesPage.totalPages,
                        isLoading = false,
                        isPaginationLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isPaginationLoading = false, error = e.message) }
            }
        }
    }
}
