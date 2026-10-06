package com.example.themoviesapp.presentation.screen.account

import com.example.themoviesapp.domain.common.Constants
import com.example.themoviesapp.presentation.common.UiText

data class AccountScreenUiState(
    val selectedLanguage: String = Constants.DEFAULT_LANGUAGE,
    val isSyncing: Boolean = false,
    val syncMessage: UiText? = null
)
