package com.example.themoviesapp.presentation.screen.auth

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.themoviesapp.MainDispatcherRule
import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey
import com.example.themoviesapp.domain.usecase.auth.AuthUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authUseCase: AuthUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        authUseCase = mockk()
        savedStateHandle = SavedStateHandle()
        viewModel = AuthViewModel(authUseCase, savedStateHandle)
    }

    @Test
    fun `ChangeApiKey intent updates apiKey in uiState`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals("", initialState.apiKey)

            viewModel.handleIntent(AuthViewModelIntents.ChangeApiKey("new_api_key"))

            val updatedState = awaitItem()
            assertEquals("new_api_key", updatedState.apiKey)
        }
    }

    @Test
    fun `ChangeAccountId intent updates accountId in uiState`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals("", initialState.accountId)

            viewModel.handleIntent(AuthViewModelIntents.ChangeAccountId("new_account_id"))

            val updatedState = awaitItem()
            assertEquals("new_account_id", updatedState.accountId)
        }
    }

    @Test
    fun `Save intent on success updates uiState accordingly`() = runTest {
        coEvery { authUseCase(any(), any()) } returns true
        
        viewModel.handleIntent(AuthViewModelIntents.ChangeApiKey("test_key"))
        viewModel.handleIntent(AuthViewModelIntents.ChangeAccountId("test_id"))

        viewModel.uiState.test {
            // Drop initial state
            val initialState = awaitItem()
            
            viewModel.handleIntent(AuthViewModelIntents.Save)
            
            // Expected loading state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            
            // Expected success state
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(true, successState.isSuccess)
            assertEquals(null, successState.error)
        }
    }

    @Test
    fun `Save intent on error updates uiState accordingly`() = runTest {
        val errorMessage = "Invalid token"
        coEvery { authUseCase(any(), any()) } throws Exception(errorMessage)
        
        viewModel.handleIntent(AuthViewModelIntents.ChangeApiKey("test_key"))
        viewModel.handleIntent(AuthViewModelIntents.ChangeAccountId("test_id"))

        viewModel.uiState.test {
            // Drop initial state
            val initialState = awaitItem()
            
            viewModel.handleIntent(AuthViewModelIntents.Save)
            
            // Expected loading state
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            
            // Expected error state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals(false, errorState.isSuccess)
            assertEquals(errorMessage, errorState.error)
        }
    }
}
