package com.kmpstudios.universalAppJc.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.ComposeHiltThemeRule
import com.kmpstudios.universalAppJc.utils.ComposeThemeRule
import com.kmpstudios.universalAppJc.utils.setAppContent
import com.kmpstudios.universalAppJc.utils.waitUntilDisplayed
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NavigationFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val themeRule = ComposeHiltThemeRule()

    private val composeTestRule = themeRule.composeTestRule

    @Test
    fun clickingProfileTab_navigatesToProfileScreen() {
        composeTestRule.setAppContent()

        composeTestRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()
    }

    @Test
    fun homeTab_isSelected_onLaunch() {
        composeTestRule.setAppContent()

        composeTestRule.waitUntilDisplayed(TestTags.HOME_SCREEN)

        composeTestRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }

    @Test
    fun profileTab_isSelected_afterNavigation() {
        composeTestRule.setAppContent()

        composeTestRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        composeTestRule.onNodeWithTag(TestTags.TAB_PROFILE).assertIsSelected()
    }

    @Test
    fun tappingSameTab_doesNotDuplicateStack() {
        composeTestRule.setAppContent()

        composeTestRule.waitUntilDisplayed(TestTags.HOME_SCREEN)

        composeTestRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()
        composeTestRule.waitUntilDisplayed(TestTags.PROFILE_SCREEN)
        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.TAB_PROFILE).performClick()

        Espresso.pressBack()

        composeTestRule.waitUntilDisplayed(TestTags.HOME_SCREEN)
        composeTestRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }
}