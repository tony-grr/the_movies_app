package com.example.themoviesapp.data.network.movie.dto

import com.example.themoviesapp.domain.entity.movie.Genre
import com.example.themoviesapp.domain.entity.movie.MovieDetails

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        adult = this.adult,
        backdropPath = this.backdropPath,
        budget = this.budget,
        genres = this.genres.map { Genre(id = it.id, name = it.name) },
        homepage = this.homepage,
        id = this.id,
        imdbId = this.imdbId,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
