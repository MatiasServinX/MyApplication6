package com.example.myapplication.ui.theme.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Paletas simples de Material 3. Se pueden personalizar sin modificar las pantallas.
private val LightColors = lightColorScheme()
private val DarkColors = darkColorScheme()

@Composable
fun RecordatoriosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
