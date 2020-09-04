package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

interface AfterValueExtractorUseCase {
    fun extract(list: List<RedditArticleListItem>): String?
}
