package fr.leboncoin.bcalbums

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.leboncoin.bcalbums.model.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class ServiceTests {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val apiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(ApiService::class.java)

    @After
    internal fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun MockWebServer.enqueueResponse(code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("success_response.json")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    @Test
    fun `should fetch movies correctly given 200 response`() {

        mockWebServer.enqueueResponse(200)

        runBlocking {
            val response = apiService.getAlbums()

            if (response.isSuccessful && response.body() != null)
                Assert.assertTrue(response.body()?.size == 5000)
            else
                Assert.fail()
        }
    }

    @Test
    fun `webservice getAlbums gives id = 200 for element 200`() {

        mockWebServer.enqueueResponse(200)

        runBlocking {

            val response = apiService.getAlbums()

            if (response.isSuccessful && response.body() != null) {

                response.body()?.let { albums ->
                    Assert.assertEquals(200, albums[199].id)
                }

            } else {

                Assert.fail()
            }
        }
    }
}