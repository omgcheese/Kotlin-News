package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

interface SubRedditListMergerUseCase {
    fun mergeOnSuccess(
        newList: List<RedditArticleListItem>,
        oldList: List<RedditArticleListItem>
    ): List<RedditArticleListItem>

    fun mergeOnFailure(existingList: List<RedditArticleListItem>): List<RedditArticleListItem>
}
