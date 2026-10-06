package com.example.themoviesapp.domain.usecase.movie

import com.example.themoviesapp.domain.common.Result
import com.example.themoviesapp.domain.entity.movie.MovieDetails
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        val lang = settingsRepository.getLanguage().first()
        return movieRepository.getMovieDetails(movieId, lang)
    }
}
