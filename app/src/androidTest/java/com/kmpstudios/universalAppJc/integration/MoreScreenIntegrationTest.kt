package com.kmpstudios.universalAppJc.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.screens.MoreScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoreScreenIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun moreScreen_displaysTaggedRoot() {
        composeTestRule.setContent {
            MoreScreen()
        }

        composeTestRule.onNodeWithTag(TestTags.MORE_SCREEN).assertIsDisplayed()
    }
}
