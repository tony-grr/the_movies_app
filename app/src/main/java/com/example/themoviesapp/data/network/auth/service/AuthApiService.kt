package com.example.themoviesapp.data.network.auth.service

import com.example.themoviesapp.data.network.auth.dto.AuthResponseDto
import retrofit2.http.GET

interface AuthApiService {
    @GET("authentication")
    suspend fun auth(): AuthResponseDto
}