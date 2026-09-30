package com.example.themoviesapp.domain.usecase.movie

import com.example.themoviesapp.domain.entity.movie.MovieDetails
import com.example.themoviesapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails {
        return movieRepository.getMovieDetails(movieId)
    }
}
