package com.example.themoviesapp.domain.usecase.auth

import com.example.themoviesapp.domain.entity.auth.ApiKey
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class GetApiKeyUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): ApiKey?{
        return authRepository.getApiKey()
    }
}