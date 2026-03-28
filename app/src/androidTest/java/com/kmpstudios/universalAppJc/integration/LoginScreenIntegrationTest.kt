package com.kmpstudios.universalAppJc.integration

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.HiltTestActivity
import com.kmpstudios.universalAppJc.di.TestApiModule
import com.kmpstudios.universalAppJc.fake.FakeResponses
import com.kmpstudios.universalAppJc.ui.navigation.Home
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.LocalRootNavigator
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.startup.LoginScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenIntegrationTest {

    private lateinit var mockWebServer: MockWebServer

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val rootNavKeys = mutableListOf<NavKey>()

    @Before
    fun setup() {
        val latch = java.util.concurrent.CountDownLatch(1)
        Thread {
            mockWebServer = MockWebServer()
            mockWebServer.start()
            TestApiModule.baseUrl = mockWebServer.url("/").toString()
            latch.countDown()
        }.apply { start() }
        latch.await()
        hiltRule.inject()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        rootNavKeys.clear()
    }

    private fun enqueueJson(body: String, code: Int = 200) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(body)
                .addHeader("Content-Type", "application/json")
        )
    }

    @Test
    fun loginScreen_success_navigatesToHome() {
        enqueueJson(FakeResponses.loginSuccessResponse)

        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { },
                LocalRootNavigator provides { key -> rootNavKeys.add(key) }
            ) {
                LoginScreen()
            }
        }

        composeTestRule.onNodeWithTag(TestTags.LOGIN_EMAIL).performTextInput("user@test.com")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_PASSWORD).performTextInput("password123")
        composeTestRule.onNodeWithTag(TestTags.LOGIN_SUBMIT).performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            rootNavKeys.any { it is Home }
        }
        assertTrue(rootNavKeys.any { it is Home })
    }
}
