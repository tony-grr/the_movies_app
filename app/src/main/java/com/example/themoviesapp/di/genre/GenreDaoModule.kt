package com.example.themoviesapp.di.genre

import com.example.themoviesapp.data.local.AppDatabase
import com.example.themoviesapp.data.local.genre.dao.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GenreDaoModule{
    @Provides
    fun provideGenreDao(database: AppDatabase): GenreDao {
        return database.genreDao()
    }
}