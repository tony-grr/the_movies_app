package com.example.themoviesapp.presentation.screen.main

sealed interface MainScreenIntents {
    object Load : MainScreenIntents
    data class ChangeSort(val sort: MoviesSort) : MainScreenIntents
    object LoadNextPage : MainScreenIntents
}
