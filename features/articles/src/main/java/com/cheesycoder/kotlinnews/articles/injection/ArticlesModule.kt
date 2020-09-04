package com.cheesycoder.kotlinnews.articles.injection

import com.cheesycoder.kotlinnews.articles.viewmodel.ArticleListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val articleModule = module {
    viewModel { ArticleListViewModel(get(), get(), get()) }
}
