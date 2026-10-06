package com.example.themoviesapp.data.local.favorite.repository

import com.example.themoviesapp.data.local.favorite.dao.FavoriteMovieDao
import com.example.themoviesapp.data.local.favorite.entity.FavoriteSyncStatus
import com.example.themoviesapp.data.local.favorite.entity.toDomain
import com.example.themoviesapp.data.local.favorite.entity.toFavoriteEntity
import com.example.themoviesapp.data.network.favorite.dto.FavoriteRequestDto
import com.example.themoviesapp.data.network.favorite.service.FavoriteApiService
import com.example.themoviesapp.data.network.movie.dto.toDomain
import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val favoriteApiService: FavoriteApiService,
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : FavoritesRepository {

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeFavoriteIds(): Flow<List<Int>> {
        return favoriteMovieDao.observeFavoriteIds()
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override suspend fun toggleFavorite(movie: Movie) {
        val isFav = favoriteMovieDao.isFavorite(movie.id)
        if (isFav) {
            favoriteMovieDao.deleteFavorite(movie.id)
            syncWithServer(movie.id, false)
        } else {
            favoriteMovieDao.insertFavorite(movie.toFavoriteEntity(FavoriteSyncStatus.PENDING))
            syncWithServer(movie.id, true)
        }
    }

    private suspend fun syncWithServer(movieId: Int, isFavorite: Boolean) {
        try {
            val accountId = authRepository.getAccountId()?.value?.toIntOrNull() ?: return
            val request = FavoriteRequestDto(
                mediaType = "movie",
                mediaId = movieId,
                favorite = isFavorite
            )
            val response = favoriteApiService.markAsFavorite(accountId, request)
            if (response.statusCode == 1 || response.statusCode == 12 || response.statusCode == 13) {
                if (isFavorite) {
                    favoriteMovieDao.updateSyncStatus(movieId, FavoriteSyncStatus.SYNCED)
                }
            } else {
                 if (isFavorite) {
                     favoriteMovieDao.updateSyncStatus(movieId, FavoriteSyncStatus.ERROR)
                 }
            }
        } catch (e: Exception) {
            if (isFavorite) {
                favoriteMovieDao.updateSyncStatus(movieId, FavoriteSyncStatus.ERROR)
            }
        }
    }

    override suspend fun refreshFavorites() {
        try {
            val accountId = authRepository.getAccountId()?.value?.toIntOrNull() ?: return
            val lang = settingsRepository.getLanguage().first()
            val response = favoriteApiService.getFavoriteMovies(accountId, language = lang)
            val favoriteEntities = response.results.map { dto ->
                dto.toDomain().toFavoriteEntity(FavoriteSyncStatus.SYNCED)
            }
            favoriteMovieDao.insertFavorites(favoriteEntities)
        } catch (e: Exception) {
            // Ошибка сети, игнорируем или можно логировать
        }
    }
}
