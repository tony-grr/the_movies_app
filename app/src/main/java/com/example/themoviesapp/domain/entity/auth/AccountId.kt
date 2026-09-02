package com.example.themoviesapp.domain.entity.auth

data class AccountId(
    val value: String
){
    companion object {
        private const val MIN_LENGTH = 1

        private val ACCOUNT_ID_REGEX = Regex("^[0-9]+$")

        fun validate(accountId: String): Boolean {
            return accountId.isNotBlank() &&
                    accountId.length >= MIN_LENGTH &&
                    accountId.matches(ACCOUNT_ID_REGEX)
        }
    }
}
