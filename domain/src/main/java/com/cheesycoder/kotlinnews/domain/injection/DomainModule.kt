package com.cheesycoder.kotlinnews.domain.injection

import com.cheesycoder.kotlinnews.domain.controller.ArticleResultFlattener
import com.cheesycoder.kotlinnews.domain.controller.ImageExtensionDetector
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCaseImpl
import org.koin.dsl.module

var domainModule = module {
    factory<SubRedditListFetchUseCase> { SubRedditListFetchUseCaseImpl(get(), get()) }
    factory { ArticleResultFlattener(get()) }
    factory { ImageExtensionDetector() }
}
