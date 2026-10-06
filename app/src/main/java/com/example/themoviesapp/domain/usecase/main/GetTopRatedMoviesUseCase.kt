package com.example.themoviesapp.domain.usecase.main

import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.MoviesPage
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(
        page: Int,
        region: String? = null
    ): Result<MoviesPage> {
        val lang = settingsRepository.getLanguage().first()
        return movieRepository.getTopRatedMovies(page, lang, region)
    }
}
