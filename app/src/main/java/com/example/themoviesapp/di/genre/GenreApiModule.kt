package com.example.themoviesapp.di.genre

import com.example.themoviesapp.data.network.genre.service.GenreApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenreApiModule {
    @Provides
    @Singleton
    fun provideGenreApiService(retrofit: Retrofit): GenreApiService{
        return retrofit.create(GenreApiService::class.java)
    }
}