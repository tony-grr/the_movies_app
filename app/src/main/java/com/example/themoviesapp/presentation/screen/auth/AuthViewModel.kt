package com.example.themoviesapp.presentation.screen.auth

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey
import com.example.themoviesapp.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val authUseCase: AuthUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private companion object {
        const val UI_STATE_KEY = "auth_ui_state"
    }

    val uiState: StateFlow<AuthScreenUiState> = savedStateHandle.getStateFlow(
        key = UI_STATE_KEY,
        initialValue = AuthScreenUiState()
    )

    fun handleIntent(intent:AuthViewModelIntents){
        val currentState = uiState.value
        val newState = updateState(currentState = currentState, intent = intent)
        savedStateHandle[UI_STATE_KEY] = newState
        handleSideEffects(intent, newState)
    }


    private fun updateState(currentState:AuthScreenUiState, intent:AuthViewModelIntents): AuthScreenUiState{
        return when(intent){
            is AuthViewModelIntents.ChangeApiKey -> {
                currentState.copy(apiKey = intent.value)
            }
            is AuthViewModelIntents.ChangeAccountId -> {
                currentState.copy(accountId = intent.value)
            }
            is AuthViewModelIntents.Save -> {
                currentState.copy(isLoading = true, error = null)
            }
        }
    }

    private fun handleSideEffects(
        intent: AuthViewModelIntents,
        newState: AuthScreenUiState
    ) {
        if (intent == AuthViewModelIntents.Save && newState.isLoading){
            viewModelScope.launch {
                try {
                    authUseCase(
                        apiKey = ApiKey(newState.apiKey),
                        accountId = AccountId(newState.accountId)
                    ).let { isSuccess->
                        savedStateHandle[UI_STATE_KEY] = newState.copy(
                            isLoading = false,
                            isSuccess = isSuccess
                        )
                    }
                }
                catch (e: Exception){
                    savedStateHandle[UI_STATE_KEY] = newState.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = e.message
                    )
                }
            }
        }
    }
}