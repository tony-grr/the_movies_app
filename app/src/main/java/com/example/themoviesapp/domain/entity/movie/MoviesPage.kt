package com.example.themoviesapp.domain.entity.movie

data class MoviesPage(
    val movies: List<Movie>,
    val currentPage: Int,
    val totalPages: Int
)
