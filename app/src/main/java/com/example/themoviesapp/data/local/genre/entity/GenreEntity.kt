package com.example.themoviesapp.data.local.genre.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviesapp.domain.entity.genre.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)

fun GenreEntity.toDomain() = Genre(id = id, name = name)

fun Genre.toEntity() = GenreEntity(id = id, name = name)
