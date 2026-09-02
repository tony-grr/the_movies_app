package com.example.themoviesapp.data.network.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val success: Boolean,
    @SerialName("status_code")
    val statusCode: Int? = null,
    @SerialName("status_message")
    val statusMessage: String? = null,
)