package com.cheesycoder.kotlinnews.data.repository

import androidx.annotation.VisibleForTesting
import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.exts.toResult
import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.data.service.RedditService
import java.io.IOException

@VisibleForTesting
val NETWORK_FAILURE_MESSAGE = "There is no interweb :("

class RedditRepositoryImpl(
    private val service: RedditService
) : RedditRepository {
    override suspend fun getArticlesOf(name: String, after: String?): Result<RedditArticles> {
        return try {
            service.getSubredditArticles(name, after).toResult()
        } catch (networkFailure: IOException) {
            Result.Error(networkFailure, NETWORK_FAILURE_MESSAGE)
        }
    }
}
