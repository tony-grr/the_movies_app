package com.example.themoviesapp.data.local.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthStorage {
    companion object {
        const val RAW_API_KEY = "raw_api_key"
        const val RAW_ACCOUNT_ID = "raw_account_id"

        const val PREFS_FILE_NAME = "secret_shared_prefs"
    }

    private val prefs: SharedPreferences by lazy {
        createEncryptedSharedPreferences()
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun save(rawApiKey: String, rawAccountId: String) {
        withContext(Dispatchers.IO) {
            prefs.edit(commit = true) {
                putString(RAW_API_KEY, rawApiKey)
                putString(RAW_ACCOUNT_ID, rawAccountId)
            }
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            prefs.edit(commit = true) {
                remove(RAW_API_KEY)
                remove(RAW_ACCOUNT_ID)
            }
        }
    }

    override suspend fun getRawAccountId(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(RAW_ACCOUNT_ID, null)
        }
    }

    override suspend fun getRawApiKey(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(RAW_API_KEY, null)
        }
    }
}