package com.cheesycoder.kotlinnews.data.model

data class ArticleData(
    val id: String,
    val title: String,
    val selftext: String?,
    val url_overridden_by_dest: String?
)
