package com.kmpstudios.universalAppJc.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.screens.profile.ProfileScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.ComposeThemeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenIntegrationTest {

    @get:Rule
    val themeRule = ComposeThemeRule()

    private val composeTestRule = themeRule.composeTestRule

    @Test
    fun profileScreen_displaysTaggedRoot() {
        themeRule.setContent {
            ProfileScreen()
        }

        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()
    }
}
