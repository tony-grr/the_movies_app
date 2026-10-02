package com.example.themoviesapp.data.local.favorite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviesapp.data.local.favorite.entity.FavoriteMovieEntity
import com.example.themoviesapp.data.local.favorite.entity.FavoriteSyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT id FROM favorite_movies")
    fun observeFavoriteIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(movies: List<FavoriteMovieEntity>)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("UPDATE favorite_movies SET syncStatus = :status WHERE id = :id")
    suspend fun updateSyncStatus(id: Int, status: FavoriteSyncStatus)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
