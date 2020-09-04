package com.cheesycoder.kotlinnews.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class RedditArticleListItem {

    @Parcelize
    data class Article(
        val id: String,
        val title: String,
        val body: String?,
        val photoUrl: String?
    ) : RedditArticleListItem(), Parcelable

    data class LoadMore(
        val after: String?
    ) : RedditArticleListItem()

    data class TryAgain(
        val after: String?
    ) : RedditArticleListItem()
}
