package com.example.themoviesapp.data.network.movie.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("budget") val budget: Long,
    @SerialName("genres") val genres: List<GenreDto>,
    @SerialName("homepage") val homepage: String?,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("revenue") val revenue: Long,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String?,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)
