package com.cheesycoder.kotlinnews.domain.injection

import com.cheesycoder.kotlinnews.domain.controller.ArticleItemTransformer
import com.cheesycoder.kotlinnews.domain.controller.ArticleResultFlattener
import com.cheesycoder.kotlinnews.domain.controller.ImageExtensionDetector
import com.cheesycoder.kotlinnews.domain.usecase.AfterValueExtractorUseCase
import com.cheesycoder.kotlinnews.domain.usecase.AfterValueExtractorUseCaseImpl
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCaseImpl
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListMergerUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListMergerUseCaseImpl
import org.koin.dsl.module

var domainModule = module {
    factory<SubRedditListFetchUseCase> { SubRedditListFetchUseCaseImpl(get(), get(), get()) }
    factory<SubRedditListMergerUseCase> { SubRedditListMergerUseCaseImpl() }
    factory<AfterValueExtractorUseCase> { AfterValueExtractorUseCaseImpl() }
    factory { ArticleResultFlattener(get()) }
    factory { ImageExtensionDetector() }
    factory { ArticleItemTransformer() }
}
