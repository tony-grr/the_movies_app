package com.example.themoviesapp.data.local.favorite.repository

import com.example.themoviesapp.data.local.favorite.dao.FavoriteMovieDao
import com.example.themoviesapp.data.local.favorite.entity.FavoriteSyncStatus
import com.example.themoviesapp.data.network.favorite.dto.FavoriteResponseDto
import com.example.themoviesapp.data.network.favorite.service.FavoriteApiService
import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryImplTest {

    private lateinit var dao: FavoriteMovieDao
    private lateinit var api: FavoriteApiService
    private lateinit var auth: AuthRepository
    private lateinit var settings: SettingsRepository
    private lateinit var repository: FavoritesRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        api = mockk()
        auth = mockk()
        settings = mockk()
        
        coEvery { auth.getAccountId() } returns AccountId("123")
        
        repository = FavoritesRepositoryImpl(dao, api, auth, settings)
    }

    @Test
    fun `toggleFavorite adds to DB and syncs when not favorite`() = runTest {
        // Arrange
        val movie = Movie(1, "Test", "Test", "Test", null, null, "2024", 5.0, 10, 1.0, emptyList(), false, false, "en")
        coEvery { dao.isFavorite(1) } returns false
        coEvery { api.markAsFavorite(123, any()) } returns FavoriteResponseDto(1, "Success")

        // Act
        repository.toggleFavorite(movie)

        // Assert
        coVerify { dao.insertFavorite(match { it.id == 1 && it.syncStatus == FavoriteSyncStatus.PENDING }) }
        coVerify { api.markAsFavorite(123, match { it.mediaId == 1 && it.favorite == true }) }
        coVerify { dao.updateSyncStatus(1, FavoriteSyncStatus.SYNCED) }
    }

    @Test
    fun `toggleFavorite sets ERROR status when sync fails on insert`() = runTest {
        // Arrange
        val movie = Movie(1, "Test", "Test", "Test", null, null, "2024", 5.0, 10, 1.0, emptyList(), false, false, "en")
        coEvery { dao.isFavorite(1) } returns false
        coEvery { api.markAsFavorite(any(), any()) } throws Exception("Network Error")

        // Act
        repository.toggleFavorite(movie)

        // Assert
        coVerify { dao.insertFavorite(match { it.syncStatus == FavoriteSyncStatus.PENDING }) }
        coVerify { dao.updateSyncStatus(1, FavoriteSyncStatus.ERROR) }
    }
    
    @Test
    fun `toggleFavorite deletes from DB and syncs when already favorite`() = runTest {
        // Arrange
        val movie = Movie(1, "Test", "Test", "Test", null, null, "2024", 5.0, 10, 1.0, emptyList(), false, false, "en")
        coEvery { dao.isFavorite(1) } returns true
        coEvery { api.markAsFavorite(123, any()) } returns FavoriteResponseDto(13, "Success delete") // 13 is success code for deletion

        // Act
        repository.toggleFavorite(movie)

        // Assert
        coVerify { dao.deleteFavorite(1) }
        coVerify { api.markAsFavorite(123, match { it.mediaId == 1 && it.favorite == false }) }
        // For deletion, syncWithServer doesn't updateSyncStatus because record is deleted
        coVerify(exactly = 0) { dao.updateSyncStatus(any(), any()) } 
    }
}
