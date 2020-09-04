package com.cheesycoder.kotlinnews.domain.controller

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.domain.util.DataFixture
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ArticleResultFlattenerTest {

    private var flattener: ArticleResultFlattener? = null
    private val urlDetector: ImageExtensionDetector = mockk()

    @Before
    fun setUp() {
        flattener = ArticleResultFlattener(urlDetector)
    }

    @Test
    fun `convert to article success result - true url detector`() {
        val id = "valueId"
        val title = "valueTitle"
        val after = "valueAfter"
        val url = "valueUrl"
        val redditArticles = DataFixture.createRedditArticles(
            id = id,
            title = title,
            after = after,
            url = url
        )
        val resultArticles = DataFixture.createResultSuccess(redditArticles)
        every { urlDetector.isEndingWithPictureExtension(any()) } returns true

        val result = flattener!!.convert(resultArticles)

        result as Result.Success
        Assert.assertEquals(result.data.after, after)
        result.data.articles.first().apply {
            Assert.assertEquals(this.id, id)
            Assert.assertEquals(this.title, title)
            Assert.assertEquals(this.photoUrl, url)
        }
    }

    @Test
    fun `convert to article success result - false url detector`() {
        val id = "valueId"
        val title = "valueTitle"
        val after = "valueAfter"
        val redditArticles = DataFixture.createRedditArticles(
            id = id,
            title = title,
            after = after,
            url = "valueUrl"
        )
        val resultArticles = DataFixture.createResultSuccess(redditArticles)
        every { urlDetector.isEndingWithPictureExtension(any()) } returns false

        val result = flattener!!.convert(resultArticles)

        result as Result.Success
        Assert.assertEquals(result.data.after, after)
        result.data.articles.first().apply {
            Assert.assertEquals(this.id, id)
            Assert.assertEquals(this.title, title)
            Assert.assertEquals(this.photoUrl, null)
        }
    }

    @Test
    fun `convert to article failure result`() {
        val resultArticles = DataFixture.createResultError<RedditArticles>()

        val result = flattener!!.convert(resultArticles)

        Assert.assertEquals(result.javaClass, Result.Error::class.java)
    }

    @After
    fun tearDown() {
        flattener = null
    }
}
