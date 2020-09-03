package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.repository.RedditRepository
import com.cheesycoder.kotlinnews.domain.controller.ArticleResultFlattener
import com.cheesycoder.kotlinnews.domain.model.RedditArticleList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubRedditListFetchUseCaseImpl(
    private val repository: RedditRepository,
    private val flattener: ArticleResultFlattener,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubRedditListFetchUseCase {
    override suspend fun execute(subRedditName: String, after: String?): Result<RedditArticleList> =
        withContext(coroutineDispatcher) {
            val rawData = repository.getArticlesOf(subRedditName, after)
            return@withContext flattener.convert(rawData)
        }
}
