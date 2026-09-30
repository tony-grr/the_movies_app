package com.example.themoviesapp.presentation.screen.movie_detail

import com.example.themoviesapp.domain.entity.movie.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)
