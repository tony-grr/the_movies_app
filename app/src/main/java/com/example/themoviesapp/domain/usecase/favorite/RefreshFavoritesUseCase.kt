package com.example.themoviesapp.domain.usecase.favorite

import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import javax.inject.Inject

class RefreshFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke() {
        favoritesRepository.refreshFavorites()
    }
}
