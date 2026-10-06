package com.example.themoviesapp.domain.repository.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
}
