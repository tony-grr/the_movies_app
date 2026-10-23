package com.example.themoviesapp.presentation.screen.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.usecase.favorite.GetFavoriteMoviesUseCase
import com.example.themoviesapp.domain.usecase.favorite.RefreshFavoritesUseCase
import com.example.themoviesapp.domain.usecase.favorite.ToggleFavoriteUseCase
import com.example.themoviesapp.domain.usecase.main.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val refreshFavoritesUseCase: RefreshFavoritesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadGenres()
        observeFavorites()
        refreshFavorites()
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            refreshFavoritesUseCase()
            // ObserveFlow updated movies, just turn off loading (flow updates automatically)
            _uiState.update { it.copy(isLoading = false) }
        }
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

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { movies ->
                _uiState.update { it.copy(movies = movies, isLoading = false) }
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movie)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Log.d("FavoritesViewModel", "toggleFavorite: ${e.message}")
            }
        }
    }
}
