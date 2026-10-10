package com.example.themoviesapp.data.network.auth.interceptor

import com.example.themoviesapp.data.local.auth.AuthStorage
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class TokenInterceptorTest {

    private lateinit var authStorage: AuthStorage
    private lateinit var interceptor: TokenInterceptor

    @Before
    fun setup() {
        authStorage = mockk()
        interceptor = TokenInterceptor(authStorage)
    }

    @Test
    fun `intercept adds Authorization header if token exists`() {
        // Arrange
        val testToken = "test_token_123"
        coEvery { authStorage.getRawApiKey() } returns testToken

        val mockRequest = Request.Builder().url("https://test.com").build()
        val mockResponse = mockk<Response>()
        val mockChain = mockk<Interceptor.Chain>()
        
        every { mockChain.request() } returns mockRequest
        
        var modifiedRequest: Request? = null
        every { mockChain.proceed(any()) } answers {
            modifiedRequest = firstArg()
            mockResponse
        }

        // Act
        val result = interceptor.intercept(mockChain)

        // Assert
        assertEquals(mockResponse, result)
        assertEquals("Bearer $testToken", modifiedRequest?.header("Authorization"))
    }

    @Test
    fun `intercept does not add Authorization header if token is null`() {
        // Arrange
        coEvery { authStorage.getRawApiKey() } returns null

        val mockRequest = Request.Builder().url("https://test.com").build()
        val mockResponse = mockk<Response>()
        val mockChain = mockk<Interceptor.Chain>()
        
        every { mockChain.request() } returns mockRequest
        
        var modifiedRequest: Request? = null
        every { mockChain.proceed(any()) } answers {
            modifiedRequest = firstArg()
            mockResponse
        }

        // Act
        val result = interceptor.intercept(mockChain)

        // Assert
        assertEquals(mockResponse, result)
        assertNull(modifiedRequest?.header("Authorization"))
    }
}
