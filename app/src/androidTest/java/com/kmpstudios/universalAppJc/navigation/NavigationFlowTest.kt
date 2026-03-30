package com.kmpstudios.universalAppJc.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.setAppContent
import com.kmpstudios.universalAppJc.utils.waitUntilDisplayed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationFlowTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun clickingMoreTab_navigatesToMoreScreen() {
        composeRule.setAppContent()

        composeRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        composeRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()
    }

    @Test
    fun homeTab_isSelected_onLaunch() {
        composeRule.setAppContent()

        composeRule.waitUntilDisplayed(TestTags.HOME_SCREEN)

        composeRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }

    @Test
    fun moreTab_isSelected_afterNavigation() {
        composeRule.setAppContent()

        composeRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        composeRule.onNodeWithTag(TestTags.TAB_PROFILE).assertIsSelected()
    }

    @Test
    fun tappingSameTab_doesNotDuplicateStack() {
        composeRule.setAppContent()

        composeRule.waitUntilDisplayed(TestTags.HOME_SCREEN)

        composeRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()
        composeRule.waitUntilDisplayed(TestTags.PROFILE_SCREEN)
        composeRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        Espresso.pressBack()

        composeRule.waitUntilDisplayed(TestTags.HOME_SCREEN)
        composeRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }
}