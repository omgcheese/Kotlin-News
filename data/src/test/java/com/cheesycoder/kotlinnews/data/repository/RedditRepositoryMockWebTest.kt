package com.cheesycoder.kotlinnews.data.repository

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.service.RedditService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RedditRepositoryMockWebTest {

    private var server = MockWebServer()
    private var repository: RedditRepository? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val service = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(RedditService::class.java)

    @Before
    fun setup() {
        repository = RedditRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        server.shutdown()
        repository = null
        Dispatchers.resetMain()
    }

    @Test
    fun `feed normal article`(): Unit = runBlocking {
        givenMockServerReturningJson("kotlin_normal_article.json")

        val result = repository!!.getArticlesOf("test sub reddit", null)

        result as Result.Success
        result.data.data.children[0].data.apply {
            Assert.assertEquals(id, "ik1x3g")
            Assert.assertEquals(title, "Kotlin Multiplatform Mobile goes Alpha – Kotlin Blog")
            Assert.assertEquals(
                url_overridden_by_dest,
                "https://blog.jetbrains.com/kotlin/2020/08/kotlin-multiplatform-mobile-goes-alpha/"
            )
            Assert.assertEquals(selftext, "")
        }
        Assert.assertEquals(result.data.data.after, "t3_ik1x3g")
    }

    @Test
    fun `feed multiple normal article`(): Unit = runBlocking {
        givenMockServerReturningJson("kotlin_multiple_normal_article.json")

        val result = repository!!.getArticlesOf("test sub reddit", null)

        result as Result.Success
        result.data.data.children[0].data.apply {
            Assert.assertEquals(id, "ik1x3g")
            Assert.assertEquals(title, "Kotlin Multiplatform Mobile goes Alpha – Kotlin Blog")
            Assert.assertEquals(
                url_overridden_by_dest,
                "https://blog.jetbrains.com/kotlin/2020/08/kotlin-multiplatform-mobile-goes-alpha/"
            )
            Assert.assertEquals(selftext, "")
        }
        result.data.data.children[1].data.apply {
            Assert.assertEquals(id, "ik1x3g")
            Assert.assertEquals(title, "Kotlin Multiplatform Mobile goes Alpha – Kotlin Blog")
            Assert.assertEquals(
                url_overridden_by_dest,
                "https://blog.jetbrains.com/kotlin/2020/08/kotlin-multiplatform-mobile-goes-alpha/"
            )
            Assert.assertEquals(selftext, "")
        }
        Assert.assertEquals(result.data.data.after, "t3_ik1x3g")
    }

    @Test
    fun `feed empty article`(): Unit = runBlocking {
        givenMockServerReturningJson("kotlin_empty_article.json")

        val result = repository!!.getArticlesOf("test sub reddit", null)

        result as Result.Success
        assert(result.data.data.children.isEmpty())
        Assert.assertEquals(result.data.data.after, "t3_ik1x3g")
    }

    private fun givenMockServerReturningJson(jsonFileName: String) {
        val json = readFileAndGenerateString(jsonFileName)
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))
    }

    private fun readFileAndGenerateString(jsonFileName: String): String {
        val fileInputStream = this.javaClass.classLoader!!.getResourceAsStream(jsonFileName)
        return fileInputStream.bufferedReader().readText()
    }
}
