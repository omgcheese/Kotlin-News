package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

interface SubRedditListFetchUseCase {
    suspend fun execute(subRedditName: String, after: String?): Result<List<RedditArticleListItem>>
}
