package com.example.themoviesapp.navigation

import kotlinx.serialization.Serializable
import com.example.themoviesapp.R

@Serializable
sealed class AppMainRoute(
    val iconResourceId: Int,
    val labelResourceId: Int
){
    @Serializable
    object Main : AppMainRoute(iconResourceId = R.drawable.house_icon, labelResourceId = R.string.main_icon_label)
    @Serializable object Favorites : AppMainRoute(iconResourceId = R.drawable.bookmark_icon, labelResourceId = R.string.favorite_icon_label)
    @Serializable object Account : AppMainRoute(iconResourceId = R.drawable.person_icon, labelResourceId = R.string.account_icon_label)

    companion object {
        val items = listOf(Main, Favorites, Account)
    }
}