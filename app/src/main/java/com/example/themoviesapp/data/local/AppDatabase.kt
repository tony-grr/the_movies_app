package com.example.themoviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviesapp.data.local.favorite.dao.FavoriteMovieDao
import com.example.themoviesapp.data.local.favorite.entity.FavoriteMovieEntity
import com.example.themoviesapp.data.local.genre.dao.GenreDao
import com.example.themoviesapp.data.local.genre.entity.GenreEntity

@Database(entities = [GenreEntity::class, FavoriteMovieEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
