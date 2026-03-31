package com.kmpstudios.universalAppJc.utils

import androidx.compose.runtime.Composable
import com.kmpstudios.universalAppJc.ui.theme.UniversalAppTheme

@Composable
fun TestThemeWrapper(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    UniversalAppTheme(
        darkTheme = darkTheme,
        content = content
    )
}