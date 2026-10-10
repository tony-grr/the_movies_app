package com.example.themoviesapp.data.local.favorite.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviesapp.data.local.AppDatabase
import com.example.themoviesapp.data.local.favorite.entity.FavoriteMovieEntity
import com.example.themoviesapp.data.local.favorite.entity.FavoriteSyncStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteMovieDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteMovieDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.favoriteMovieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadFavorite() = runTest {
        val favorite = FavoriteMovieEntity(
            id = 1,
            title = "Test",
            originalTitle = "Test",
            overview = "Test overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2024",
            voteAverage = 8.0,
            voteCount = 100,
            popularity = 1.0,
            genreIds = listOf(1, 2),
            adult = false,
            video = false,
            originalLanguage = "en",
            syncStatus = FavoriteSyncStatus.SYNCED
        )
        
        dao.insertFavorite(favorite)
        
        val favorites = dao.getAllFavorites().first()
        assertEquals(1, favorites.size)
        assertEquals(favorite.id, favorites[0].id)
    }

    @Test
    fun isFavoriteReturnsTrueIfInserted() = runTest {
        val favorite = FavoriteMovieEntity(
            id = 1,
            title = "Test",
            originalTitle = "Test",
            overview = "Test overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2024",
            voteAverage = 8.0,
            voteCount = 100,
            popularity = 1.0,
            genreIds = listOf(1, 2),
            adult = false,
            video = false,
            originalLanguage = "en",
            syncStatus = FavoriteSyncStatus.SYNCED
        )
        
        dao.insertFavorite(favorite)
        
        assertTrue(dao.isFavorite(1))
        assertFalse(dao.isFavorite(2))
    }

    @Test
    fun updateSyncStatusChangesStatus() = runTest {
        val favorite = FavoriteMovieEntity(
            id = 1,
            title = "Test",
            originalTitle = "Test",
            overview = "Test overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2024",
            voteAverage = 8.0,
            voteCount = 100,
            popularity = 1.0,
            genreIds = listOf(1, 2),
            adult = false,
            video = false,
            originalLanguage = "en",
            syncStatus = FavoriteSyncStatus.PENDING
        )
        
        dao.insertFavorite(favorite)
        dao.updateSyncStatus(1, FavoriteSyncStatus.SYNCED)
        
        val favorites = dao.getAllFavorites().first()
        assertEquals(FavoriteSyncStatus.SYNCED, favorites[0].syncStatus)
    }
}
