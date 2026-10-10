package com.example.themoviesapp.presentation.screen.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginButtonIsDisabledWhenFieldsAreEmpty() {
        val uiState = AuthScreenUiState(apiKey = "", accountId = "", isLoading = false)
        var buttonText = ""

        composeTestRule.setContent {
            buttonText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.do_auth_btn_text)
            AuthScreenContent(
                uiState = uiState,
                onIntent = {}
            )
        }

        composeTestRule.onNodeWithText(buttonText).assertIsNotEnabled()
    }

    @Test
    fun loginButtonIsEnabledWhenFieldsAreFilled() {
        val uiState = AuthScreenUiState(apiKey = "key123", accountId = "acc123", isLoading = false)
        var buttonText = ""

        composeTestRule.setContent {
            buttonText = androidx.compose.ui.res.stringResource(com.example.themoviesapp.R.string.do_auth_btn_text)
            AuthScreenContent(
                uiState = uiState,
                onIntent = {}
            )
        }

        composeTestRule.onNodeWithText(buttonText).assertIsEnabled()
    }

    @Test
    fun errorMessageIsDisplayedWhenErrorIsNotNull() {
        val errorMessage = "Invalid Credentials"
        val uiState = AuthScreenUiState(error = errorMessage)

        composeTestRule.setContent {
            AuthScreenContent(
                uiState = uiState,
                onIntent = {}
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}