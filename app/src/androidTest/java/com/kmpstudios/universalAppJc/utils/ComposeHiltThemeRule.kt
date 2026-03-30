package com.kmpstudios.universalAppJc.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kmpstudios.universalAppJc.HiltTestActivity
import com.kmpstudios.universalAppJc.ui.theme.UniversalAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class ComposeHiltThemeRule(
    private val darkTheme: Boolean = false
) : TestRule {

    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    override fun apply(base: Statement, description: Description): Statement {
        return composeTestRule.apply(base, description)
    }

    fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            UniversalAppTheme(darkTheme = darkTheme) {
                content()
            }
        }
    }
}