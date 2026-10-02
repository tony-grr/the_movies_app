package com.example.themoviesapp.presentation.screen.favorites

import com.example.themoviesapp.domain.entity.movie.Movie

data class FavoritesUiState(
    val movies: List<Movie> = emptyList(),
    val genresMap: Map<Int, String> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
