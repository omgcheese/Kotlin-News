package com.cheesycoder.kotlinnews.domain.util

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.model.ArticleData
import com.cheesycoder.kotlinnews.data.model.ArticleDataWrapper
import com.cheesycoder.kotlinnews.data.model.ListingData
import com.cheesycoder.kotlinnews.data.model.RedditArticles

object DataFixture {
    fun createRedditArticles(
        id: String = "test_id",
        title: String = "test_title",
        body: String? = null,
        url: String? = null,
        after: String? = null
    ): RedditArticles {
        val articleData = ArticleData(id, title, body, url)
        val articleWrapper = ArticleDataWrapper(articleData)
        val listOfArticleWrappers = listOf(articleWrapper)
        val listingData = ListingData(listOfArticleWrappers, after)
        return RedditArticles(listingData)
    }

    fun <T> createResultSuccess(data: T): Result<T> {
        return Result.Success(data)
    }

    fun <T> createResultError(cause: Throwable = Exception(), reason: String? = null): Result<T> {
        return Result.Error(cause, reason)
    }
}
