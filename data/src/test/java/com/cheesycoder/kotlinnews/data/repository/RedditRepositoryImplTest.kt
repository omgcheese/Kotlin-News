package com.cheesycoder.kotlinnews.data.repository

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.data.service.RedditService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RedditRepositoryImplTest {

    private var repository: RedditRepository? = null
    private val service: RedditService = mockk()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        repository = RedditRepositoryImpl(service)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        repository = null
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getArticlesOf - error response should return error result`() =
        testCoroutineDispatcher.runBlockingTest {
            coEvery { service.getSubredditArticles(any(), any()) } returns Response.error(
                400,
                ResponseBody.create(null, "")
            )

            val result = repository!!.getArticlesOf("Test name", null)

            Assert.assertEquals(result.javaClass, Result.Error::class.java)
        }

    @Test
    fun `getArticleOf - success api should return success result`() =
        testCoroutineDispatcher.runBlockingTest {
            val mockedArticles = mockk<RedditArticles>()
            coEvery { service.getSubredditArticles(any(), any()) } returns Response.success(
                mockedArticles
            )

            val result = repository!!.getArticlesOf("Test name", null)

            result as Result.Success
            Assert.assertEquals(result.data, mockedArticles)
        }

    @Test
    fun `getArticleOf - success but empty result should return error result`() =
        testCoroutineDispatcher.runBlockingTest {
            coEvery { service.getSubredditArticles(any(), any()) } returns Response.success(null)

            val result = repository!!.getArticlesOf("Test name", null)

            Assert.assertEquals(result.javaClass, Result.Error::class.java)
        }

    @Test
    fun `getArticleOf - network failure should return error result`() =
        testCoroutineDispatcher.runBlockingTest {
            coEvery { service.getSubredditArticles(any(), any()) } throws IOException()

            val result = repository!!.getArticlesOf("Test name", null)

            result as Result.Error
            Assert.assertEquals(result.reason, NETWORK_FAILURE_MESSAGE)
        }
}
