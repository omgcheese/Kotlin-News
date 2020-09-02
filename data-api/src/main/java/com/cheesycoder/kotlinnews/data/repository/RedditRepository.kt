package com.cheesycoder.kotlinnews.data.repository

import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.data.model.Result

interface RedditRepository {
    suspend fun getArticlesOf(name: String, after: String?): Result<RedditArticles>
}
