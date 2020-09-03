package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.domain.model.RedditArticleList

interface SubRedditListFetchUseCase {
    suspend fun execute(subRedditName: String, after: String?): Result<RedditArticleList>
}
