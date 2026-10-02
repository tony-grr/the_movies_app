package com.example.themoviesapp.di.favorite

import com.example.themoviesapp.data.local.AppDatabase
import com.example.themoviesapp.data.local.favorite.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FavoriteDaoModule {
    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }
}
