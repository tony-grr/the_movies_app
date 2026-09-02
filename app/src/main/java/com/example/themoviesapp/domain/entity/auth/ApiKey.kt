package com.example.themoviesapp.domain.entity.auth

data class ApiKey(
    val value: String
) {
    companion object {
        private const val MIN_LENGTH = 32

        private val API_KEY_REGEX = Regex("^[A-Za-z0-9]+$")

        fun validate(apiKey: String): Boolean {
            return apiKey.isNotBlank() &&
                    apiKey.length >= MIN_LENGTH &&
                    apiKey.matches(API_KEY_REGEX)
        }
    }
}
