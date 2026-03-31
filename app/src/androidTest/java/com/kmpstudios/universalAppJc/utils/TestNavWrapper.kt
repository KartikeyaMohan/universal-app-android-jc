package com.kmpstudios.universalAppJc.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.LocalOnBack
import com.kmpstudios.universalAppJc.ui.navigation.LocalRootNavigator
import com.kmpstudios.universalAppJc.ui.navigation.NavKey

@Composable
fun TestNavWrapper(
    onNavigate: (NavKey) -> Unit = {},
    onRootNavigate: (NavKey) -> Unit = {},
    onBack: () -> Unit = {},
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavigator provides onNavigate,
        LocalRootNavigator provides onRootNavigate,
        LocalOnBack provides onBack,
        content = content
    )
}