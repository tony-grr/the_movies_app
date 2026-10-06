package com.example.themoviesapp.data.network.movie

import com.example.themoviesapp.data.network.movie.dto.toDomain
import com.example.themoviesapp.data.network.movie.service.MovieApiService
import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.MovieDetails
import com.example.themoviesapp.domain.entity.movie.MoviesPage
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int, language: String, region: String?): Result<MoviesPage> {
        return try {
            val response = movieApiService.getPopularMovies(language, page, region)
            Result.Success(
                MoviesPage(
                    movies = response.results.map { it.toDomain() },
                    currentPage = response.page,
                    totalPages = response.totalPages
                )
            )
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown Error", e)
        }
    }

    override suspend fun getTopRatedMovies(page: Int, language: String, region: String?): Result<MoviesPage> {
        return try {
            val response = movieApiService.getTopRatedMovies(language, page, region)
            Result.Success(
                MoviesPage(
                    movies = response.results.map { it.toDomain() },
                    currentPage = response.page,
                    totalPages = response.totalPages
                )
            )
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown Error", e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int, language: String): Result<MovieDetails> {
        return try {
            Result.Success(movieApiService.getMovieDetails(movieId, language).toDomain())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown Error", e)
        }
    }
}
