package com.kmpstudios.universalAppJc.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColorScheme(
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val backgroundElevated: Color,

    val surfaceDefault: Color,
    val surfaceBorder: Color,

    val brandAccent: Color,
    val brandAccentVariant: Color,

    val textPrimary: Color,
    val textSecondary: Color,

    val inputBackground: Color,
    val inputBorder: Color,

    val divider: Color,
)

val LocalCustomColorScheme = staticCompositionLocalOf<CustomColorScheme> {
    error("No CustomColorScheme provided — did you wrap your app in UniversalAppTheme?")
}

object AppTheme {
    val colors: CustomColorScheme
        @Composable get() = LocalCustomColorScheme.current
}