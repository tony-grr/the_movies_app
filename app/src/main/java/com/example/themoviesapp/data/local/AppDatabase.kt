package com.example.themoviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviesapp.data.local.genre.dao.GenreDao
import com.example.themoviesapp.data.local.genre.entity.GenreEntity

@Database(entities = [GenreEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}
