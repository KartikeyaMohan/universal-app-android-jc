package com.kmpstudios.universalAppJc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kmpstudios.universalAppJc.ui.theme.AppTheme
import com.kmpstudios.universalAppJc.ui.views.Tabs
import com.kmpstudios.universalAppJc.ui.views.TopBar
import com.kmpstudios.universalAppJc.ui.views.homeTab

@Composable
fun BaseScreen(showTopBar: Boolean = false,
               content: @Composable BoxScope.() -> Unit
) {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundPrimary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (showTopBar) {
                TopBar()
            }
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    }
}