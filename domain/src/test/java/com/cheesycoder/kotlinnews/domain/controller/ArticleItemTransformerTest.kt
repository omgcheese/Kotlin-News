package com.cheesycoder.kotlinnews.domain.controller

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.domain.model.RedditArticleList
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import com.cheesycoder.kotlinnews.domain.util.DataFixture
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ArticleItemTransformerTest {

    private var transformer: ArticleItemTransformer? = null

    @Before
    fun setUp() {
        transformer = ArticleItemTransformer()
    }

    @Test
    fun `bypass error result`() {
        val cause = IllegalAccessError()
        val reason = "test"
        val errorResult = Result.Error<RedditArticleList>(cause, reason)

        val result = transformer!!.transform(errorResult)

        result as Result.Error
        Assert.assertEquals(result.reason, reason)
        Assert.assertEquals(result.cause, cause)
    }

    @Test
    fun `transform to RedditArticleListItem-Article - null afterValue`() {
        val inputList = Result.Success(DataFixture.createRedditArticleList())

        val result = transformer!!.transform(inputList)
        result as Result.Success

        Assert.assertEquals(result.data.size, 1)
    }

    @Test
    fun `transform to RedditArticleListItem-Article - non-null afterValue`() {
        val inputList = Result.Success(DataFixture.createRedditArticleList(after = "nasjdqw"))

        val result = transformer!!.transform(inputList)
        result as Result.Success

        Assert.assertEquals(result.data.size, 2)
        Assert.assertEquals(result.data.last().javaClass, RedditArticleListItem.LoadMore::class.java)
    }

    @Test
    fun `transform to RedditArticleListItem-Article - empty afterValue`() {
        val inputList = Result.Success(DataFixture.createRedditArticleList(after = ""))

        val result = transformer!!.transform(inputList)
        result as Result.Success

        Assert.assertEquals(result.data.size, 1)
        Assert.assertNotEquals(result.data.last().javaClass, RedditArticleListItem.LoadMore::class.java)
    }

    @Test
    fun `transform to RedditArticleListItem-Article - blank afterValue`() {
        val inputList = Result.Success(DataFixture.createRedditArticleList(after = "   "))

        val result = transformer!!.transform(inputList)
        result as Result.Success

        Assert.assertEquals(result.data.size, 1)
        Assert.assertNotEquals(result.data.last().javaClass, RedditArticleListItem.LoadMore::class.java)
    }

    @After
    fun tearDown() {
        transformer = null
    }
}
