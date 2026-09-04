package com.example.themoviesapp.data.network.genre.dto

import com.example.themoviesapp.domain.entity.genre.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id:Int,
    val name: String
)

fun GenreDto.toDomain(): Genre = Genre(this.id, this.name)
