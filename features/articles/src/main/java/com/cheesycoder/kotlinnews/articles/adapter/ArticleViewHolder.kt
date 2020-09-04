package com.cheesycoder.kotlinnews.articles.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import kotlinx.android.synthetic.main.item_photo_article.view.*
import kotlinx.android.synthetic.main.item_text_article.view.*

sealed class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class TextArticle(itemView: View) : ArticleViewHolder(itemView) {
        fun bind(data: RedditArticleListItem.Article, onClick: (RedditArticleListItem) -> Unit) =
            with(itemView) {
                article_title.text = data.title
                setOnClickListener {
                    onClick(data)
                }
            }
    }

    class PhotoArticle(itemView: View) : ArticleViewHolder(itemView) {
        fun bind(data: RedditArticleListItem.Article, onClick: (RedditArticleListItem) -> Unit) =
            with(itemView) {
                photo_article_title.text = data.title
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .into(photo_article_image_view)
                setOnClickListener {
                    onClick(data)
                }
            }
    }

    class LoadMore(itemView: View) : ArticleViewHolder(itemView)
    class TryAgain(itemView: View) : ArticleViewHolder(itemView) {
        fun bind(data: RedditArticleListItem.TryAgain, onClick: (RedditArticleListItem) -> Unit) {
            itemView.setOnClickListener {
                onClick(data)
            }
        }
    }
}
