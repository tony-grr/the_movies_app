package com.example.themoviesapp.domain.repository.favorite

import com.example.themoviesapp.domain.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun observeFavoriteIds(): Flow<List<Int>>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun isFavorite(movieId: Int): Boolean
    suspend fun refreshFavorites()
}
