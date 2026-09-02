package com.example.themoviesapp.presentation.screen.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthScreenUiState(
    val apiKey: String = "",
    val accountId: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error:String? = null
): Parcelable
