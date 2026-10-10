package com.example.themoviesapp.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coursebox.presentation.component.AppTextField
import com.example.coursebox.presentation.component.AppTextFieldLabel
import com.example.themoviesapp.R

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccessAuth:()->Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess){
            onSuccessAuth()
        }
    }
    AuthScreenContent(
        uiState = uiState,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun AuthScreenContent(
    uiState: AuthScreenUiState,
    onIntent: (AuthViewModelIntents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column {
            Text(
                text = stringResource(R.string.auth_title),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 28.dp)
            )
            AppTextField(
                value = uiState.apiKey,
                onValueChange = { onIntent(AuthViewModelIntents.ChangeApiKey(it)) },
                minLines = 4,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(bottom = 16.dp),
                label = {
                    AppTextFieldLabel(
                        text = stringResource(R.string.api_key_input_caption),
                    )
                },
            )
            AppTextField(
                value = uiState.accountId,
                onValueChange = { onIntent(AuthViewModelIntents.ChangeAccountId(it)) },
                singleLine = true,
                modifier = Modifier.padding(bottom = 24.dp),
                label = {
                    AppTextFieldLabel(
                        text = stringResource(R.string.account_id_input_caption),
                    )
                },
            )
            Button(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = !uiState.isLoading && uiState.apiKey.isNotBlank() && uiState.accountId.isNotBlank(),
                onClick = { onIntent(AuthViewModelIntents.Save) }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                else {
                    Text(
                        text = stringResource(R.string.do_auth_btn_text),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
            uiState.error?.let { errorTxt->
                Text(
                    text = errorTxt,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Red
                )
            } ?: Spacer(modifier = Modifier.height(16.dp))

        }
    }
}