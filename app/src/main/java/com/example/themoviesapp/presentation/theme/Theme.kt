package com.example.themoviesapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    secondary = White,
    tertiary = Green,
    background = Dark,
    surface = DarkGray,
    onPrimary = LightGray,
    scrim = Glass,
    outline = Stroke
)

private val LightColorScheme = lightColorScheme(
    primary = Dark,
    secondary = White,
    tertiary = Green,
    background = Dark,
    surface = DarkGray,
    onPrimary = LightGray,
    scrim = Glass,
    outline = Stroke,

    )

@Composable
fun TheMoviesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}