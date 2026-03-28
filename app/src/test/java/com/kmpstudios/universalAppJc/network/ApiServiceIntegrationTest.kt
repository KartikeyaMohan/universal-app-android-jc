package com.kmpstudios.universalAppJc.network

import com.kmpstudios.universalAppJc.data.models.auth.LoginRequest
import com.kmpstudios.universalAppJc.data.models.locations.LocationData
import com.kmpstudios.universalAppJc.data.models.locations.LocationRequest
import com.kmpstudios.universalAppJc.data.network.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiServiceIntegrationTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true; isLenient = true }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getMovies returns parsed response correctly`() = runTest {
        val json = """
            {
              "status": 200,
              "data": {
                "list": [
                  { "id": 1, "name": "Tenet", "heroImageUrl": "", "rating": 4.0 }
                ],
                "meta": { "prevPage": null, "nextPage": 2 }
              }
            }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json")
        )

        val response = apiService.getMovies(page = 1, limit = 10)

        assertTrue(response.isSuccessful)
        assertEquals(200, response.body()?.status)
        assertEquals("Tenet", response.body()?.data?.list?.first()?.name)
    }

    @Test
    fun `getMovieDetails returns parsed response correctly`() = runTest {
        val json = """
            {
              "status": 200,
              "data": {
                "id": 1, "name": "Tenet",
                "description": "A thriller", "rating": 4.0,
                "trailerUrl": "", "casts": []
              }
            }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json")
        )

        val response = apiService.getMovieDetails(id = 1L)

        assertTrue(response.isSuccessful)
        assertEquals("Tenet", response.body()?.data?.name)
    }

    @Test
    fun `getMovies sends correct query parameters`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(
            """{ "status": 200, "data": null }""".trimIndent())
        )
        apiService.getMovies(page = 2, limit = 20)
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertTrue(request.path?.contains("page=2") == true)
        assertTrue(request.path?.contains("limit=20") == true)
    }

    @Test
    fun `getMovies handles 500 server error`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val response = apiService.getMovies(1, 10)
        assertFalse(response.isSuccessful)
        assertEquals(500, response.code())
    }

    @Test
    fun `login returns parsed tokens`() = runTest {
        val json = """
            {
              "status": 200,
              "data": {
                "id": 1,
                "tokens": { "auth_token": "a", "refresh_token": "r" }
              }
            }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json")
        )

        val response = apiService.login(LoginRequest("e@test.com", "p"))

        assertTrue(response.isSuccessful)
        assertEquals("a", response.body()?.data?.tokens?.authToken)
    }

    @Test
    fun `getProfile returns parsed profile`() = runTest {
        val json = """
            {
              "status": 200,
              "data": { "name": "User", "email": "u@test.com", "profile_image_url": null }
            }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json")
        )

        val response = apiService.getProfile()

        assertTrue(response.isSuccessful)
        assertEquals("User", response.body()?.data?.name)
    }

    @Test
    fun `postLocations returns message`() = runTest {
        val json = """{ "status": 200, "data": { "message": "accepted" } }"""
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json")
        )

        val response = apiService.postLocations(
            LocationRequest(listOf(LocationData("d", "t", 0.0, 0.0, 1f)))
        )

        assertTrue(response.isSuccessful)
        assertEquals("accepted", response.body()?.data?.message)
    }

    @Test
    fun `getMovies handles network timeout`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBody(
                """{ "status": 500 }""".trimIndent())
                .setBodyDelay(70, TimeUnit.SECONDS)
        )
        assertThrows(IOException::class.java) {
            runBlocking { apiService.getMovies(1, 10) }
        }
    }
}