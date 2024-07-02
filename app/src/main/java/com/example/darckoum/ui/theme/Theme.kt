package com.example.darckoum.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val colorScheme = darkColorScheme(
    primary = C1,
    onPrimary = C3,
    primaryContainer = C1,
    onPrimaryContainer = C3,
    secondary = C1,
    onSecondary = C3,
    secondaryContainer = C1,
    onSecondaryContainer = C3,
    tertiary = C1,
    onTertiary = C3,
    tertiaryContainer = C1,
    onTertiaryContainer = C3,
    surface = C2,
    onSurface = C3,
    onSurfaceVariant = C1,
    background = C2,
    onBackground = C3,
    surfaceContainer = C5,
    outline = C1,
    outlineVariant = C5,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

