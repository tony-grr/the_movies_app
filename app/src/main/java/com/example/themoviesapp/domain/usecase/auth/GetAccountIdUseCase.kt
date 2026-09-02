package com.example.themoviesapp.domain.usecase.auth

import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class GetAccountIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AccountId?{
        return authRepository.getAccountId()
    }
}