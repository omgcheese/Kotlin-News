package com.cheesycoder.kotlinnews

import android.app.Application
import com.cheesycoder.kotlinnews.articles.injection.articleModule
import com.cheesycoder.kotlinnews.data.injection.dataModule
import com.cheesycoder.kotlinnews.domain.injection.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                articleModule,
                domainModule,
                dataModule
            )
        }
    }
}
