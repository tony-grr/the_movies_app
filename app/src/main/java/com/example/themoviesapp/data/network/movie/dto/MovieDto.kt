package com.example.themoviesapp.data.network.movie.dto

import com.example.themoviesapp.domain.entity.movie.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    val popularity: Double,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val adult: Boolean,
    val video: Boolean,
    @SerialName("original_language") val originalLanguage: String
)

fun MovieDto.toDomain() = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    popularity = popularity,
    genreIds = genreIds,
    adult = adult,
    video = video,
    originalLanguage = originalLanguage
)
