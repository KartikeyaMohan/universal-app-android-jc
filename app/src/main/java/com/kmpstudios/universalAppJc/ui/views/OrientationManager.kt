package com.kmpstudios.universalAppJc.ui.views

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberOrientationManager(): (Boolean) -> Unit {
    val context = LocalContext.current
    return remember {
        {   isFullScreen ->
                val activity = context as? Activity
                activity?.requestedOrientation = if (isFullScreen) {
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
                else {
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
        }
    }
}