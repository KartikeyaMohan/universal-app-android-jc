package com.kmpstudios.universalAppJc.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.navigation.Login
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.profile.ProfileScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.ComposeHiltThemeRule
import com.kmpstudios.universalAppJc.utils.ComposeThemeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileScreenIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val themeRule = ComposeHiltThemeRule()

    private val composeTestRule = themeRule.composeTestRule

    @Test
    fun profileScreen_displaysTaggedRoot() {
        themeRule.setContent {
            ProfileScreen()
        }

        composeTestRule.onNodeWithTag(TestTags.PROFILE_SCREEN).assertIsDisplayed()
    }

    @Test
    fun profileScreen_logoutNavigatesToLogin() {
        var navigatedTo: NavKey? = null

        themeRule.setContent(
            onRootNavigate = { key -> navigatedTo = key }
        ) {
            ProfileScreen()
        }

        composeTestRule.onNodeWithTag(TestTags.LOGOUT_CLICK).performClick()
        assert(navigatedTo is Login)
    }
}
