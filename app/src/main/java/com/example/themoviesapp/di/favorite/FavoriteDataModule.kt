package com.example.themoviesapp.di.favorite

import com.example.themoviesapp.data.local.favorite.repository.FavoritesRepositoryImpl
import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteDataModule {
    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}
