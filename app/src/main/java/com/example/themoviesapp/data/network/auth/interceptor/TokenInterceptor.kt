package com.example.themoviesapp.data.network.auth.interceptor

import com.example.themoviesapp.data.local.auth.AuthStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//Подставляет в каждый запрос токен авторизации перед его отправкой
class TokenInterceptor @Inject constructor(
    private val authStorage: AuthStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = runBlocking { authStorage.getRawApiKey() }
        val request = if (token != null) {
            original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else original
        return chain.proceed(request)
    }
}