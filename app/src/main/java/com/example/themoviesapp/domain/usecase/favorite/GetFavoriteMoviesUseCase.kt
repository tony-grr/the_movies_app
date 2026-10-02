package com.example.themoviesapp.domain.usecase.favorite

import com.example.themoviesapp.domain.entity.movie.Movie
import com.example.themoviesapp.domain.repository.favorite.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return favoritesRepository.getFavoriteMovies()
    }
}
