package com.example.themoviesapp.data.network.genre.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenresResponseDto(
    val genres:List<GenreDto>
)
