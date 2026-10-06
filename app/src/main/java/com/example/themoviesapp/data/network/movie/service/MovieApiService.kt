package com.example.themoviesapp.data.network.movie.service

import com.example.themoviesapp.data.network.movie.dto.MovieDetailsDto
import com.example.themoviesapp.data.network.movie.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ): MovieResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDetailsDto
}
