package com.example.themoviesapp.domain.repository.genre

import com.example.themoviesapp.domain.entity.genre.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<List<Genre>>
    suspend fun getGenresCount(): Int
    suspend fun refreshGenres()
    suspend fun getGenreById(id: Int): Genre?
}
