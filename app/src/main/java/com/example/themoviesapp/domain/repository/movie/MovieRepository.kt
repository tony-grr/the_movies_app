package com.example.themoviesapp.domain.repository.movie

import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.MovieDetails
import com.example.themoviesapp.domain.entity.movie.MoviesPage

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1, language: String, region: String? = null): Result<MoviesPage>
    suspend fun getTopRatedMovies(page: Int = 1, language: String, region: String? = null): Result<MoviesPage>
    suspend fun getMovieDetails(movieId: Int, language: String): Result<MovieDetails>
}
