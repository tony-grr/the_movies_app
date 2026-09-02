package com.example.themoviesapp.di.auth

import com.example.themoviesapp.data.local.auth.AuthRepositoryImpl
import com.example.themoviesapp.data.local.auth.AuthStorage
import com.example.themoviesapp.data.local.auth.AuthStorageImpl
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    //привязка интерфейса AuthStorage к реализации AuthStorageImpl
    //создает синглтон объект AuthStorageImpl и предоставляет везде, где требуется AuthStorage
    @Binds
    @Singleton
    abstract fun bindsAuthStorage(impl: AuthStorageImpl): AuthStorage

    //привязка интерфейса AuthRepository к реализации AuthRepositoryImpl
    //создает синглтон объект AuthRepositoryImpl и предоставляет везде, где требуется AuthRepository
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}