package com.kmpstudios.universalAppJc.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.theme.UniversalAppTheme
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class ComposeThemeRule(
    private val darkTheme: Boolean = false
) : TestRule {

    val composeTestRule = createComposeRule()

    override fun apply(base: Statement, description: Description): Statement {
        return composeTestRule.apply(base, description)
    }

    fun setContent(
        onNavigate: (NavKey) -> Unit = {},
        onRootNavigate: (NavKey) -> Unit = {},
        onBack: () -> Unit = {},
        content: @Composable () -> Unit) {
        composeTestRule.setContent {
            UniversalAppTheme(darkTheme = darkTheme) {
                TestNavWrapper(
                    onNavigate = onNavigate,
                    onRootNavigate = onRootNavigate,
                    onBack = onBack
                ) {
                    content()
                }
            }
        }
    }
}