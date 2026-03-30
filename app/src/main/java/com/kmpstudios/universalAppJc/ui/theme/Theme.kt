package com.kmpstudios.universalAppJc.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary      = DodgerBlue,
    secondary    = WindsorBlue,
    tertiary     = DodgerBlue,
    background   = BlueCharcoal,
    surface      = WindsorBlue,
    onPrimary    = FullWhite,
    onBackground = AlpineWhite,
    onSurface    = AlpineWhite,
    outline      = ThirtyThreeFullWhite,
)

private val LightColorScheme = lightColorScheme(
    primary      = RainbowBlue,
    secondary    = AthensGray,
    tertiary     = RainbowBlue,
    background   = SnowWhite,
    surface      = FullWhite,
    onPrimary    = FullWhite,
    onBackground = ExtremeNavy,
    onSurface    = ExtremeNavy,
    outline      = OneAAfricanTurquoise,
)

private val DarkCustomColors = CustomColorScheme(
    backgroundPrimary   = BlueCharcoal,
    backgroundSecondary = WindsorBlue,
    backgroundElevated  = UniformBlue,
    surfaceDefault      = WindsorBlue,
    surfaceBorder       = ThirtyThreeFullWhite,
    brandAccent         = DodgerBlue,
    brandAccentVariant  = RainbowBlue,
    textPrimary         = AlpineWhite,
    textSecondary       = NeutralLightBlue,
    inputBackground     = UniformBlue,
    inputBorder         = ThirtyThreeFullWhite,
    divider             = ThirtyThreeFullWhite,
)

private val LightCustomColors = CustomColorScheme(
    backgroundPrimary   = SnowWhite,
    backgroundSecondary = AthensGray,
    backgroundElevated  = FullWhite,
    surfaceDefault      = FullWhite,
    surfaceBorder       = OneAAfricanTurquoise,
    brandAccent         = RainbowBlue,
    brandAccentVariant  = DodgerBlue,
    textPrimary         = ExtremeNavy,
    textSecondary       = SharkGray,
    inputBackground     = AthensGray,
    inputBorder         = OneAAfricanTurquoise,
    divider             = OneAAfricanTurquoise,
)

@Composable
fun UniversalAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // keep false to honour your palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColorScheme provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            content     = content
        )
    }
}