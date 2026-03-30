package com.kmpstudios.universalAppJc.integration

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.di.TestApiModule
import com.kmpstudios.universalAppJc.fake.FakeResponses
import com.kmpstudios.universalAppJc.ui.navigation.LocalNavigator
import com.kmpstudios.universalAppJc.ui.navigation.MovieDetails
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.movies.MovieScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.utils.ComposeHiltThemeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieScreenIntegrationTest {

    private lateinit var mockWebServer: MockWebServer

    private val themeRule = ComposeHiltThemeRule()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule(order = 1)
    val composeTestRule = themeRule.composeTestRule

    private val navigatedKeys = mutableListOf<NavKey>()

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
        navigatedKeys.clear()
    }

    private fun setMovieScreenContent() {
        themeRule.setContent {
            CompositionLocalProvider(
                LocalNavigator provides { key -> navigatedKeys.add(key) }
            ) {
                MovieScreen()
            }
        }
    }

    private fun enqueueMockResponse(body: String, responseCode: Int = 200) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(body)
                .addHeader("Content-Type", "application/json")
        )
    }

    // ─────────────────────────────────────────────
    // Loading state
    // ─────────────────────────────────────────────

    @Test
    fun movieScreen_showsLoadingIndicator_initially() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeResponses.moviesSuccessResponse)
                .setBodyDelay(2, TimeUnit.SECONDS)
        )

        setMovieScreenContent()

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────
    // Success state
    // ─────────────────────────────────────────────

    @Test
    fun movieScreen_showsMovieList_onSuccess() {
        enqueueMockResponse(FakeResponses.moviesSuccessResponse)

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_ITEM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_LIST)
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithTag(TestTags.MOVIE_ITEM)
            .assertCountEquals(5)
    }

    @Test
    fun movieScreen_movieItem_displaysNameAndRating() {
        enqueueMockResponse(FakeResponses.moviesSuccessResponse)

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_ITEM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // First item name and rating are visible
        composeTestRule
            .onAllNodesWithTag(TestTags.MOVIE_NAME, useUnmergedTree = true)
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithTag(TestTags.MOVIE_RATING, useUnmergedTree = true)
            .onFirst()
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────
    // Navigation
    // ─────────────────────────────────────────────

    @Test
    fun movieScreen_clickingItem_navigatesToMovieDetails() {
        enqueueMockResponse(FakeResponses.moviesSuccessResponse)

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_ITEM)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onAllNodesWithTag(TestTags.MOVIE_ITEM)
            .onFirst()
            .performClick()
        assertTrue(navigatedKeys.isNotEmpty())
        assertTrue(navigatedKeys.first() is MovieDetails)
    }

    // ─────────────────────────────────────────────
    // Error state
    // ─────────────────────────────────────────────

    @Test
    fun movieScreen_showsErrorMessage_onServerError() {
        enqueueMockResponse(FakeResponses.moviesErrorResponse, responseCode = 500)

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.ERROR_MESSAGE)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertIsDisplayed()
    }

    @Test
    fun movieScreen_showsErrorMessage_onNetworkFailure() {
        // Simulate network failure
        mockWebServer.enqueue(
            MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        )

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.ERROR_MESSAGE)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_MESSAGE)
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────
    // Pagination
    // ─────────────────────────────────────────────

    @Test
    fun movieScreen_showsAppendLoadingIndicator_whenLoadingMorePages() {
        // First page
        enqueueMockResponse(FakeResponses.moviesSuccessResponse)
        // Second page delayed to keep append loading visible
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeResponses.moviesSuccessResponse)
                .setBodyDelay(3, TimeUnit.SECONDS)
        )

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_ITEM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Scroll to bottom to trigger append load
        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_LIST)
            .performScrollToIndex(4)

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun movieScreen_showsAppendError_whenLoadMoreFails() {
        // First page succeeds
        enqueueMockResponse(FakeResponses.moviesSuccessResponse)
        // Second page fails
        mockWebServer.enqueue(
            MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        )

        setMovieScreenContent()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_ITEM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_LIST)
            .performScrollToIndex(4)

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.ERROR_LOAD_MORE)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_LOAD_MORE)
            .assertIsDisplayed()
    }
}