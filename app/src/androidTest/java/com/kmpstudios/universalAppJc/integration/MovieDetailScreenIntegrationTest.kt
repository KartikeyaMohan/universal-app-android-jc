package com.kmpstudios.universalAppJc.integration

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmpstudios.universalAppJc.di.TestApiModule
import com.kmpstudios.universalAppJc.fake.FakeResponses
import com.kmpstudios.universalAppJc.ui.navigation.NavKey
import com.kmpstudios.universalAppJc.ui.screens.movies.MovieDetailScreen
import com.kmpstudios.universalAppJc.ui.utils.TestTags
import com.kmpstudios.universalAppJc.ui.viewmodels.MovieDetailsViewModel
import com.kmpstudios.universalAppJc.utils.ComposeHiltThemeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
class MovieDetailScreenIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private val themeRule = ComposeHiltThemeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule(order = 1)
    val composeTestRule = themeRule.composeTestRule

    private lateinit var mockWebServer: MockWebServer

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
    fun detailScreen_showsLoadingIndicator_initially() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FakeResponses.movieDetailSuccessResponse)
                .setBodyDelay(2, TimeUnit.SECONDS)
        )

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────
    // Success state
    // ─────────────────────────────────────────────

    @Test
    fun detailScreen_showsMovieName_onSuccess() {
        enqueueMockResponse(FakeResponses.movieDetailSuccessResponse)

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_NAME)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_NAME)
            .assertIsDisplayed()
    }

    @Test
    fun detailScreen_showsRating_onSuccess() {
        enqueueMockResponse(FakeResponses.movieDetailSuccessResponse)

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_RATING)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_RATING)
            .assertIsDisplayed()
    }

    @Test
    fun detailScreen_showsDescription_onSuccess() {
        enqueueMockResponse(FakeResponses.movieDetailSuccessResponse)

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.MOVIE_DESCRIPTION)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_DESCRIPTION)
            .assertIsDisplayed()
    }

    @Test
    fun detailScreen_showsCastList_onSuccess() {
        enqueueMockResponse(FakeResponses.movieDetailSuccessResponse)

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.CAST_LIST)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.CAST_LIST)
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithTag(TestTags.CAST_ITEM)
            .assertCountEquals(3)
    }

    // ─────────────────────────────────────────────
    // Error state
    // ─────────────────────────────────────────────

    @Test
    fun detailScreen_showsLoadingIndicator_onError() {
        // On error, movieDetailResponse stays null → loading indicator stays visible
        enqueueMockResponse(FakeResponses.movieDetailErrorResponse, responseCode = 404)

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.LOADING_INDICATOR)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()

        // Movie content must not be visible
        composeTestRule
            .onNodeWithTag(TestTags.MOVIE_NAME)
            .assertDoesNotExist()
    }

    @Test
    fun detailScreen_showsLoadingIndicator_onNetworkFailure() {
        mockWebServer.enqueue(
            MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        )

        themeRule.setContent {
            val viewModel: MovieDetailsViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailsViewModel.HiltFactory ->
                    factory.create(1L)
                }
            )
            MovieDetailScreen(movieDetailsViewModel = viewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag(TestTags.LOADING_INDICATOR)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(TestTags.LOADING_INDICATOR)
            .assertIsDisplayed()
    }
}