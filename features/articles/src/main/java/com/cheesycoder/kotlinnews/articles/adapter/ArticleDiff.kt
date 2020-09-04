package com.cheesycoder.kotlinnews.articles.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

class ArticleDiff : DiffUtil.ItemCallback<RedditArticleListItem>() {
    override fun areItemsTheSame(
        oldItem: RedditArticleListItem,
        newItem: RedditArticleListItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: RedditArticleListItem,
        newItem: RedditArticleListItem
    ): Boolean {
        return oldItem == newItem
    }
}
