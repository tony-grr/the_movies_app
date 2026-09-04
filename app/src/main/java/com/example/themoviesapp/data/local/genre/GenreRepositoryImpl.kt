package com.example.themoviesapp.data.local.genre

import com.example.themoviesapp.data.local.genre.dao.GenreDao
import com.example.themoviesapp.data.local.genre.entity.toDomain
import com.example.themoviesapp.data.local.genre.entity.toEntity
import com.example.themoviesapp.data.network.genre.dto.toDomain
import com.example.themoviesapp.data.network.genre.service.GenreApiService
import com.example.themoviesapp.domain.entity.genre.Genre
import com.example.themoviesapp.domain.repository.genre.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiService: GenreApiService,
    private val genreDao: GenreDao
) : GenreRepository {

    override fun getGenres(): Flow<List<Genre>> {
        return genreDao.getAllGenres().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshGenres() {
        try {
            val response = apiService.getList()
            val entities = response.genres.map { dto ->
                dto.toDomain().toEntity()
            }
            genreDao.insertGenres(entities)
        } catch (e: Exception) {
            // здесь должна быть более детальная обработка ошибок
            throw e
        }
    }

    override suspend fun getGenreById(id: Int): Genre? {
        return genreDao.getGenreById(id)?.toDomain()
    }
}