package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

class SubRedditListMergerUseCaseImpl : SubRedditListMergerUseCase {

    override fun mergeOnSuccess(
        newList: List<RedditArticleListItem>,
        oldList: List<RedditArticleListItem>
    ): List<RedditArticleListItem> {
        val filteredOldList = oldList.filterNot {
            it is RedditArticleListItem.LoadMore || it is RedditArticleListItem.TryAgain
        }
        val mergedList = mutableListOf<RedditArticleListItem>()
        mergedList += filteredOldList
        mergedList += newList
        return mergedList
    }

    override fun mergeOnFailure(existingList: List<RedditArticleListItem>): List<RedditArticleListItem> {
        val afterValue = (existingList.last() as? RedditArticleListItem.TryAgain)?.after
        val filteredExistingList = existingList.filterNot {
            it is RedditArticleListItem.LoadMore || it is RedditArticleListItem.TryAgain
        }
        val mergedErrorList = mutableListOf<RedditArticleListItem>()
        mergedErrorList += filteredExistingList
        mergedErrorList += RedditArticleListItem.TryAgain(afterValue)
        return mergedErrorList
    }
}
