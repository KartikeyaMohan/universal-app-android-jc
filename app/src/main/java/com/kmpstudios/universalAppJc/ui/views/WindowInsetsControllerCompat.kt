package com.kmpstudios.universalAppJc.ui.views

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun rememberWindowInsetsControllerCompat(): WindowInsetsControllerCompat {
    val view = LocalView.current
    return remember(view) {
        WindowCompat.getInsetsController(
            (view.context as Activity).window,
            view
        )
    }
}