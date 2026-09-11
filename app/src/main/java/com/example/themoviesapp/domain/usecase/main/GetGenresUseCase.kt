package com.example.themoviesapp.domain.usecase.main

import com.example.themoviesapp.domain.entity.genre.Genre
import com.example.themoviesapp.domain.repository.genre.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    suspend operator fun invoke(): Flow<List<Genre>> {
        if (genreRepository.getGenresCount() == 0){
            genreRepository.refreshGenres()
        }
        return genreRepository.getGenres()
    }
}