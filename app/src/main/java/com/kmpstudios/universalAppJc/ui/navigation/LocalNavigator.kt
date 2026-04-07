package com.kmpstudios.universalAppJc.ui.navigation

import androidx.compose.runtime.compositionLocalOf

typealias Navigator = (NavKey) -> Unit

val LocalNavigator = compositionLocalOf<Navigator> {
    error("No Navigator provided")
}
val LocalRootNavigator = compositionLocalOf<Navigator> {
    error("No root navigator")
}
val LocalOnBack = compositionLocalOf<() -> Unit> {
    error("No back handler")
}