package com.example.themoviesapp.domain.usecase.favorite

import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteIdsUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return favoritesRepository.observeFavoriteIds()
    }
}
