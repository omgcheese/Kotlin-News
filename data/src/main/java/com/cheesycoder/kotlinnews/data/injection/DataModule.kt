package com.cheesycoder.kotlinnews.data.injection

import com.cheesycoder.kotlinnews.data.BuildConfig
import com.cheesycoder.kotlinnews.data.repository.RedditRepository
import com.cheesycoder.kotlinnews.data.repository.RedditRepositoryImpl
import com.cheesycoder.kotlinnews.data.service.RedditService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    single<RedditRepository> { RedditRepositoryImpl(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single { get<Retrofit>().create(RedditService::class.java) }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}
