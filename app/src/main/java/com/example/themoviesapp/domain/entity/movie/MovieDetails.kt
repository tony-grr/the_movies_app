package com.example.themoviesapp.domain.entity.movie

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String
)
