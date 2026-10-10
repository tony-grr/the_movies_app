package com.example.themoviesapp.presentation.component

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coursebox.presentation.component.AppTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appTextFieldDisplaysValueAndLabel() {
        composeTestRule.setContent {
            AppTextField(
                value = "Test Input",
                onValueChange = {},
                label = { Text("Label Text") }
            )
        }

        composeTestRule.onNodeWithText("Label Text").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Input").assertIsDisplayed()
    }

    @Test
    fun appTextFieldDisplaysPlaceholderWhenEmpty() {
        composeTestRule.setContent {
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Placeholder Text") }
            )
        }

        composeTestRule.onNodeWithText("Placeholder Text").assertIsDisplayed()
    }
}
