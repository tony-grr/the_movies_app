package com.example.themoviesapp.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.usecase.favorite.ObserveFavoriteIdsUseCase
import com.example.themoviesapp.domain.usecase.favorite.ToggleFavoriteUseCase
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
    private val getGenresUseCase: GetGenresUseCase,
    private val observeFavoriteIdsUseCase: ObserveFavoriteIdsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadGenres()
        observeFavorites()
        handleIntent(MainScreenIntents.Load)
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            observeFavoriteIdsUseCase().collect { ids ->
                _uiState.update { it.copy(favoriteIds = ids.toSet()) }
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            try {
                getGenresUseCase().collect { genres ->
                    _uiState.update { state ->
                        state.copy(genresMap = genres.associate { it.id to it.name })
                    }
                }
            } catch (e: Exception) {
                //TODO придумать как обрабатывать ошибки загрузки списка жанров
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
            is MainScreenIntents.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(intent.movie)
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

            val pageToLoad = if (isPagination) _uiState.value.currentPage + 1 else 1
            val result = when (_uiState.value.sort) {
                MoviesSort.ByPopular -> getPopularMoviesUseCase(page = pageToLoad)
                MoviesSort.ByTopRated -> getTopRatedMoviesUseCase(page = pageToLoad)
            }

            when (result) {
                is Result.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            movies = if (isPagination) state.movies + result.data.movies else result.data.movies,
                            currentPage = result.data.currentPage,
                            totalPages = result.data.totalPages,
                            isLoading = false,
                            isPaginationLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, isPaginationLoading = false, error = result.message) }
                }
            }
        }
    }
}
