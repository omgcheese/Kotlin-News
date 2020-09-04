package com.cheesycoder.kotlinnews.domain.model

data class RedditArticle(
    val id: String,
    val title: String,
    val body: String?,
    val photoUrl: String?
)
