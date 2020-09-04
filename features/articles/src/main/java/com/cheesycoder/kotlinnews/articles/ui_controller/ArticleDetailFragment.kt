package com.cheesycoder.kotlinnews.articles.ui_controller

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cheesycoder.kotlinnews.articles.R
import com.cheesycoder.kotlinnews.articles.common.NavArgument
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import kotlinx.android.synthetic.main.fragment_article_detail.*

class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private val arg by lazy {
        arguments
            ?.getParcelable<RedditArticleListItem.Article>(NavArgument.articleDetailArgumentKey)
            ?: throw IllegalArgumentException("Argument is missing!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        article_detail_tool_bar.title = arg.title
        article_detail_tool_bar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        if (arg.photoUrl.isNullOrBlank()) {
            article_detail_image_view.isVisible = false
        } else {
            Glide.with(this)
                .load(arg.photoUrl)
                .into(article_detail_image_view)
        }
        article_detail_body.text = arg.body
    }
}
