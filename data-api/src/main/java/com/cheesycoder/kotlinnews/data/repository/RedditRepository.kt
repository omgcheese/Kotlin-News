package com.cheesycoder.kotlinnews.data.repository

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.model.RedditArticles

interface RedditRepository {
    suspend fun getArticlesOf(name: String, after: String?): Result<RedditArticles>
}
