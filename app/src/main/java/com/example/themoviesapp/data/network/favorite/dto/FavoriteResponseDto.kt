package com.example.themoviesapp.data.network.favorite.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponseDto(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
)
