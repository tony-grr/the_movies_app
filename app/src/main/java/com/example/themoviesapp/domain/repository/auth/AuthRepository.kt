package com.example.themoviesapp.domain.repository.auth

import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val isAuthorized: StateFlow<Boolean>
    suspend fun auth(apiKey: ApiKey, accountId: AccountId): Boolean

    suspend fun logout()
    suspend fun getApiKey(): ApiKey?
    suspend fun getAccountId(): AccountId?

    suspend fun checkTokenAndAccountIdExistence(): Boolean
}