package com.cheesycoder.kotlinnews.data.model

data class ListingData(
    val children: List<ArticleDataWrapper>,
    val after: String
)