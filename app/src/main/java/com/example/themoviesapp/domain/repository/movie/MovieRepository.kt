package com.example.themoviesapp.domain.repository.movie

import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.entity.movie.MoviesPage

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1, language: String = "ru-RU", region: String? = null): MoviesPage
    suspend fun getTopRatedMovies(page: Int = 1, language: String = "ru-RU", region: String? = null): MoviesPage
}
