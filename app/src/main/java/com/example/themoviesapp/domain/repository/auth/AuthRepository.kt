package com.example.themoviesapp.domain.repository.auth

import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey

interface AuthRepository {
    suspend fun auth(apiKey: ApiKey, accountId: AccountId): Boolean
    suspend fun save(apiKey: ApiKey, accountId: AccountId)
    suspend fun getApiKey(): ApiKey?
    suspend fun getAccountId(): AccountId?
    suspend fun delete()
    suspend fun exists(): Boolean{
        return getApiKey() != null && getAccountId() != null
    }
}