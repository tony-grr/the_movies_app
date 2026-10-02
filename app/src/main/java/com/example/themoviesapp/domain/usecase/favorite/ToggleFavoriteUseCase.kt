package com.example.themoviesapp.domain.usecase.favorite

import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        favoritesRepository.toggleFavorite(movie)
    }
}
