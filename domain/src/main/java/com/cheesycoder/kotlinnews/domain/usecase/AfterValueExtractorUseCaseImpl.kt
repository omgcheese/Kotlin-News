package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

class AfterValueExtractorUseCaseImpl : AfterValueExtractorUseCase {
    override fun extract(list: List<RedditArticleListItem>): String? {
        return when (val lastItem = list.last()) {
            is RedditArticleListItem.LoadMore -> lastItem.after
            is RedditArticleListItem.TryAgain -> lastItem.after
            else -> return null
        }
    }
}
