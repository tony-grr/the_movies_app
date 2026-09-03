package com.example.themoviesapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRootRoute{
    @Serializable
    object Auth: AppRootRoute

    @Serializable
    object AppContainer: AppRootRoute
}