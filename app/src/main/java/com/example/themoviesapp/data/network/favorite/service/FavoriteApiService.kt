package com.example.themoviesapp.data.network.favorite.service

import com.example.themoviesapp.data.network.favorite.dto.FavoriteRequestDto
import com.example.themoviesapp.data.network.favorite.dto.FavoriteResponseDto
import com.example.themoviesapp.data.network.movie.dto.MovieResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoriteApiService {
    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Path("account_id") accountId: Int,
        @Body request: FavoriteRequestDto
    ): FavoriteResponseDto

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): MovieResponseDto
}
