package com.example.themoviesapp.di.database

import android.content.Context
import androidx.room.Room
import com.example.themoviesapp.data.local.AppDatabase
import com.example.themoviesapp.data.local.genre.dao.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movies_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}
