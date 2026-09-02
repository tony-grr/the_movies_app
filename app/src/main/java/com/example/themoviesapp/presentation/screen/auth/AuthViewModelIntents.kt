package com.example.themoviesapp.presentation.screen.auth

sealed interface AuthViewModelIntents {
    data class ChangeApiKey(val value: String) : AuthViewModelIntents
    data class ChangeAccountId(val value: String) : AuthViewModelIntents
    object Save : AuthViewModelIntents
}