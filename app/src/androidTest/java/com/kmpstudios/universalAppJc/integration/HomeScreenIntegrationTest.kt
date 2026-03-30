package com.kmpstudios.universalAppJc.integration

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.Location
import com.kmpstudios.universalAppJc.ui.navigation.Profile
import com.kmpstudios.universalAppJc.ui.navigation.Movie
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.home.HomeScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.ComposeThemeRule
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenIntegrationTest {

    @get:Rule
    val themeRule = ComposeThemeRule()

    private val composeTestRule = themeRule.composeTestRule

    @Test
    fun homeScreen_displaysCards() {
        themeRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { }
            ) {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_MOVIE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_LOCATION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_PROFILE).assertIsDisplayed()
    }

    @Test
    fun homeScreen_movieCard_navigatesToMovie() {
        val keys = mutableListOf<NavKey>()
        themeRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { keys.add(it) }
            ) {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_MOVIE).performClick()
        assertTrue(keys.first() is Movie)
    }

    @Test
    fun homeScreen_locationCard_navigatesToLocation() {
        val keys = mutableListOf<NavKey>()
        themeRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { keys.add(it) }
            ) {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_LOCATION).performClick()
        assertTrue(keys.first() is Location)
    }

    @Test
    fun homeScreen_profileCard_navigatesToProfile() {
        val keys = mutableListOf<NavKey>()
        themeRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { keys.add(it) }
            ) {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTags.CARD_HOME_TO_PROFILE).performClick()
        assertTrue(keys.first() is Profile)
    }
}
