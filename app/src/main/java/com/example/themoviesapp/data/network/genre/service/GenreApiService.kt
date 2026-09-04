package com.example.themoviesapp.data.network.genre.service

import com.example.themoviesapp.data.network.genre.dto.GenresResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApiService {

    @GET("genre/movie/list")
    suspend fun getList(
        @Query("language") language: String = "ru"
    ): GenresResponseDto
}