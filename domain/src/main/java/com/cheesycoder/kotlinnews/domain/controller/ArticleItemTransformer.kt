package com.cheesycoder.kotlinnews.domain.controller

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.domain.model.RedditArticleList
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

class ArticleItemTransformer {
    fun transform(raw: Result<RedditArticleList>): Result<List<RedditArticleListItem>> {
        return when (raw) {
            is Result.Error -> Result.Error(raw.cause, raw.reason)
            is Result.Success -> {
                val rawData = raw.data
                val redditItemList = mutableListOf<RedditArticleListItem>()
                val transformedData = rawData.articles.map {
                    RedditArticleListItem.Article(
                        it.id,
                        it.title,
                        it.body,
                        it.photoUrl
                    )
                }
                redditItemList += transformedData
                if (!rawData.after.isNullOrBlank()) {
                    redditItemList += RedditArticleListItem.LoadMore(rawData.after)
                }
                Result.Success(redditItemList)
            }
        }
    }
}
