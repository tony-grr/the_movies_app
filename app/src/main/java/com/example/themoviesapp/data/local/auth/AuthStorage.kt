package com.example.themoviesapp.data.local.auth

interface AuthStorage {
    suspend fun save(rawApiKey: String, rawAccountId: String)
    suspend fun getRawApiKey(): String?
    suspend fun getRawAccountId(): String?
    suspend fun deleteAll()
}