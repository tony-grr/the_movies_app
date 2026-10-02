package com.example.themoviesapp.data.local.favorite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviesapp.domain.entity.movie.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val genreIds: List<Int>,
    val adult: Boolean,
    val video: Boolean,
    val originalLanguage: String,
    val syncStatus: FavoriteSyncStatus
)

fun Movie.toFavoriteEntity(syncStatus: FavoriteSyncStatus): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        popularity = this.popularity,
        genreIds = this.genreIds,
        adult = this.adult,
        video = this.video,
        originalLanguage = this.originalLanguage,
        syncStatus = syncStatus
    )
}

fun FavoriteMovieEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        popularity = this.popularity,
        genreIds = this.genreIds,
        adult = this.adult,
        video = this.video,
        originalLanguage = this.originalLanguage
    )
}
