package com.kmpstudios.universalAppJc.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.setAppContent
import com.kmpstudios.universalAppJc.utils.waitUntilDisplayed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationRenderTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeScreen_isShowOnLaunch() {
        composeRule.setAppContent()

        composeRule.waitUntilDisplayed(TestTags.HOME_SCREEN)

        composeRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }

    @Test
    fun bottomBar_isVisible_withBothTabs() {
        composeRule.setAppContent()

        composeRule.onNodeWithTag(TestTags.TAB_HOME).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TAB_MORE).assertIsDisplayed()
    }
}