package com.example.themoviesapp.presentation.screen.main

import com.example.themoviesapp.domain.entity.movie.Movie

sealed interface MainScreenIntents {
    object Load : MainScreenIntents
    data class ChangeSort(val sort: MoviesSort) : MainScreenIntents
    object LoadNextPage : MainScreenIntents
    data class ToggleFavorite(val movie: Movie): MainScreenIntents
}
