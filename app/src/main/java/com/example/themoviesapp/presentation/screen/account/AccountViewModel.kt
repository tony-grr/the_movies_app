package com.example.themoviesapp.presentation.screen.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.R
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import com.example.themoviesapp.domain.repository.settings.SettingsRepository
import com.example.themoviesapp.domain.usecase.favorite.RefreshFavoritesUseCase
import com.example.themoviesapp.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val refreshFavoritesUseCase: RefreshFavoritesUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getLanguage().collect { lang ->
                _uiState.update { it.copy(selectedLanguage = lang) }
            }
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
        }
    }

    fun syncFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, syncMessage = null) }
            try {
                refreshFavoritesUseCase()
                _uiState.update { it.copy(isSyncing = false, syncMessage = UiText.StringResource(R.string.sync_success)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSyncing = false, syncMessage = UiText.StringResource(R.string.sync_error, e.message ?: "Unknown error")) }
            }
        }
    }

    fun clearSyncMessage() {
        _uiState.update { it.copy(syncMessage = null) }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutComplete()
        }
    }
}
