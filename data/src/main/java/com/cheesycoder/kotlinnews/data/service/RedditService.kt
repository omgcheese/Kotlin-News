package com.cheesycoder.kotlinnews.data.service

import com.cheesycoder.kotlinnews.data.model.RedditArticles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {
    @GET("r/{subredditName}/.json")
    suspend fun getSubredditArticles(
        @Path("subredditName") subredditName: String,
        @Query("after") after: String?
    ): Response<RedditArticles>
}
