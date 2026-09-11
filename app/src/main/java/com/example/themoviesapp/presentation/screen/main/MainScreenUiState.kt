package com.example.themoviesapp.presentation.screen.main

import com.example.themoviesapp.domain.entity.movie.Movie

data class MainScreenUiState(
    val movies: List<Movie> = emptyList(),
    val genresMap: Map<Int, String> = emptyMap(),
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val sort: MoviesSort = MoviesSort.ByPopular,
    val error: String? = null
)
