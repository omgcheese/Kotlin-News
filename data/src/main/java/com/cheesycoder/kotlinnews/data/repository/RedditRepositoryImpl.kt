package com.cheesycoder.kotlinnews.data.repository

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.exts.toResult
import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.data.service.RedditService

class RedditRepositoryImpl(
    private val service: RedditService
) : RedditRepository {
    override suspend fun getArticlesOf(name: String, after: String?): Result<RedditArticles> {
        return service.getSubredditArticles(name, after).toResult()
    }
}
