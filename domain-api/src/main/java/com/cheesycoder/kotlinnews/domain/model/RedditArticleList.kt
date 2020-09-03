package com.cheesycoder.kotlinnews.domain.model

data class RedditArticleList(
    val after: String?,
    val articles: List<RedditArticle>
)
