package com.example.themoviesapp.domain.usecase.main

import com.example.themoviesapp.domain.entity.movie.MoviesPage
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        page: Int,
        language: String = "ru-RU",
        region: String? = null
    ): MoviesPage {
        return movieRepository.getTopRatedMovies(page, language, region)
    }
}
