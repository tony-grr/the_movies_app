package com.example.themoviesapp.presentation.screen.splashscreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.themoviesapp.presentation.theme.DarkGray
import com.example.themoviesapp.presentation.theme.Green

@Composable
fun SplashScreen() {
    // 1. Создаем бесконечную анимацию
    val infiniteTransition = rememberInfiniteTransition(label = "GradientTransition")

    // 2. Анимируем значение от 0 до 1000 для смещения координат
    val targetOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse // Плавно возвращает анимацию назад
        ),
        label = "GradientOffset"
    )

    // 3. Создаем линейный градиент с динамическим смещением
    val gradientBrush = Brush.linearGradient(
        colors = listOf(DarkGray, Green, DarkGray),
        start = Offset(x = targetOffset, y = 0f),
        end = Offset(x = targetOffset + 500f, y = targetOffset + 1000f)
    )

    // 4. Контейнер на весь экран с анимированным фоном
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    )
}
