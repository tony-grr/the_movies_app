package com.example.themoviesapp.data.local.auth

import android.util.Log
import com.example.themoviesapp.data.network.auth.service.AuthApiService
import com.example.themoviesapp.domain.entity.auth.AccountId
import com.example.themoviesapp.domain.entity.auth.ApiKey
import com.example.themoviesapp.domain.repository.auth.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val storage: AuthStorage
) : AuthRepository {
    override suspend fun auth(apiKey: ApiKey, accountId: AccountId): Boolean {
        return try {
            //сохранение токена в encrypted shared preferences
            //далее при выполнении запроса проверки авторизации, токен будет подставлен в заголовок запроса
            save(apiKey, accountId)
            apiService.auth().success
        } catch (e: HttpException) {
            Log.d("network debug", "http exception ${e.message}")
            when (e.code()) {
                401 -> {
                    //если пришел ответ с кодом 401, то необходимо удалить сохраненный токен
                    delete()
                    throw SecurityException("Неверный токен")
                }
                else -> throw IOException("Ошибка сервера: ${e.code()} ${e.message()}")
            }
        } catch (e: IOException) {
            Log.d("network debug", "io exception ${e.message}")
            throw IOException("Ошибка: ${e.message}", e)
        }
    }

    override suspend fun save(apiKey: ApiKey, accountId: AccountId) {
        storage.save(apiKey.value, accountId.value)
    }

    override suspend fun delete() {
        storage.deleteAll()
    }

    override suspend fun getApiKey(): ApiKey? {
        return storage.getRawApiKey()?.let { ApiKey(it) }
    }

    override suspend fun getAccountId(): AccountId? {
        return storage.getRawAccountId()?.let { AccountId(it) }
    }
}