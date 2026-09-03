package com.example.themoviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isAuthDataExistingChecked = MutableStateFlow(false)
    val isAuthDataExistingChecked = _isAuthDataExistingChecked.asStateFlow()

    val isAuthorized = authRepository.isAuthorized

    init {
        checkTokenAndAccountIdExistence()
    }

    private fun checkTokenAndAccountIdExistence(){
        viewModelScope.launch {
            //проверка того что токен и id профиля уже были сохранены и существуют
            //при этом обновляется флаг авторизации
            authRepository.checkTokenAndAccountIdExistence()
            delay(500.milliseconds)
            _isAuthDataExistingChecked.update {
                true
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}