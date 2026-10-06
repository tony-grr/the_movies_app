package com.example.themoviesapp.domain.usecase.main

import com.example.themoviesapp.domain.entity.genre.Genre
import com.example.themoviesapp.domain.repository.genre.GenreRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genreRepository: GenreRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<List<Genre>> {
        if (genreRepository.getGenresCount() == 0){
            val lang = settingsRepository.getLanguage().first()
            genreRepository.refreshGenres(lang)
        }
        return genreRepository.getGenres()
    }
}