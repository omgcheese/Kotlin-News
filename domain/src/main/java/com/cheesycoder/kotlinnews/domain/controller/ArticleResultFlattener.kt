package com.cheesycoder.kotlinnews.domain.controller

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.model.RedditArticles
import com.cheesycoder.kotlinnews.domain.model.RedditArticle
import com.cheesycoder.kotlinnews.domain.model.RedditArticleList

class ArticleResultFlattener(
    private val imageExtensionDetector: ImageExtensionDetector
) {
    fun convert(result: Result<RedditArticles>): Result<RedditArticleList> {
        return when (result) {
            is Result.Error -> Result.Error(result.cause, result.reason)
            is Result.Success -> {
                val after = result.data.data.after
                val redditArticles = result.data.data.children.map {
                    val articleData = it.data
                    val isPhotoExtension =
                        imageExtensionDetector
                            .isEndingWithPictureExtension(articleData.url_overridden_by_dest)
                    RedditArticle(
                        articleData.id,
                        articleData.title,
                        articleData.selftext,
                        if (isPhotoExtension) articleData.url_overridden_by_dest else null
                    )
                }
                Result.Success(RedditArticleList(after, redditArticles))
            }
        }
    }
}
