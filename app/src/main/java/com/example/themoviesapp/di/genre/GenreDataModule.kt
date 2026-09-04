package com.example.themoviesapp.di.genre

import com.example.themoviesapp.data.local.genre.GenreRepositoryImpl
import com.example.themoviesapp.domain.repository.genre.GenreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GenreDataModule {

    @Binds
    @Singleton
    abstract fun bindGenreRepository(impl: GenreRepositoryImpl): GenreRepository
}
