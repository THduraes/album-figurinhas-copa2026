package com.grupo.albumfigurinhas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AlbumColors = lightColorScheme(
    primary = Color(0xFF006B54),
    onPrimary = Color.White,
    secondary = Color(0xFF805600),
    onSecondary = Color.White,
    tertiary = Color(0xFF9C4146),
    background = Color(0xFFF7F9F5),
    surface = Color(0xFFF7F9F5),
    surfaceVariant = Color(0xFFE0E5DF),
)

@Composable
fun AlbumFigurinhasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AlbumColors,
        content = content,
    )
}
