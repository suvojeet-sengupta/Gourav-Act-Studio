package com.suvojeet.gouravactstudio.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent,
    secondary = DarkSurface,
    tertiary = DarkAccent,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkTextColor,
    onSecondary = DarkTextColor,
    onTertiary = DarkTextColor,
    onBackground = DarkTextColor,
    onSurface = DarkTextColor
)

private val LightColorScheme = lightColorScheme(
    primary = LightAccent,
    secondary = LightSecondaryText,
    tertiary = LightAccent,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightPrimaryText,
    onSecondary = LightPrimaryText,
    onTertiary = LightPrimaryText,
    onBackground = LightPrimaryText,
    onSurface = LightPrimaryText
)

@Composable
fun GouravActStudioTheme(
    darkTheme: Boolean = false, // Always use light theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}