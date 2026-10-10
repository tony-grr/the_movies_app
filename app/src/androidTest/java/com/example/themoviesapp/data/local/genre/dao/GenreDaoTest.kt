package com.example.themoviesapp.data.local.genre.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.themoviesapp.data.local.AppDatabase
import com.example.themoviesapp.data.local.genre.entity.GenreEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GenreDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: GenreDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.genreDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadGenres() = runTest {
        val genres = listOf(
            GenreEntity(1, "Action"),
            GenreEntity(2, "Comedy")
        )
        
        dao.insertGenres(genres)
        
        val loaded = dao.getAllGenres().first()
        assertEquals(2, loaded.size)
        assertEquals("Action", loaded.find { it.id == 1 }?.name)
        assertEquals("Comedy", loaded.find { it.id == 2 }?.name)
    }
}
