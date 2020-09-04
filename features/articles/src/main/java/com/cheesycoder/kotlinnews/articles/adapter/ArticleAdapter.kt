package com.cheesycoder.kotlinnews.articles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.cheesycoder.kotlinnews.articles.R
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem

class ArticleAdapter(
    private val onClickArticle: (RedditArticleListItem) -> Unit
) : ListAdapter<RedditArticleListItem, ArticleViewHolder>(ArticleDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ArticleType.values()[viewType]) {
            ArticleType.PHOTO_ARTICLE -> ArticleViewHolder.PhotoArticle(
                inflater.inflate(R.layout.item_photo_article, parent, false)
            )
            ArticleType.TEXT_ARTICLE -> ArticleViewHolder.TextArticle(
                inflater.inflate(R.layout.item_text_article, parent, false)
            )
            ArticleType.LOAD_MORE -> ArticleViewHolder.LoadMore(
                inflater.inflate(R.layout.item_load_more, parent, false)
            )
            ArticleType.TRY_AGAIN -> ArticleViewHolder.TryAgain(
                inflater.inflate(R.layout.item_try_again, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder.PhotoArticle -> holder.bind(
                getItem(position) as RedditArticleListItem.Article,
                onClickArticle
            )
            is ArticleViewHolder.TextArticle -> holder.bind(
                getItem(position) as RedditArticleListItem.Article,
                onClickArticle
            )
            is ArticleViewHolder.TryAgain -> holder.bind(
                getItem(position) as RedditArticleListItem.TryAgain,
                onClickArticle
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return (
            when (val item = getItem(position)) {
                is RedditArticleListItem.Article -> {
                    if (item.photoUrl.isNullOrBlank()) {
                        ArticleType.TEXT_ARTICLE
                    } else {
                        ArticleType.PHOTO_ARTICLE
                    }
                }
                is RedditArticleListItem.LoadMore -> ArticleType.LOAD_MORE
                is RedditArticleListItem.TryAgain -> ArticleType.TRY_AGAIN
            }
            ).ordinal
    }
}
