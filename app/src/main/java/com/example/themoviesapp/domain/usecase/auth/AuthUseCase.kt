package com.example.themoviesapp.domain.usecase.auth

import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        apiKey: ApiKey,
        accountId: AccountId
    ): Boolean {
        return authRepository.auth(
            apiKey,
            accountId
        )
    }
}