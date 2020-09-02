package com.cheesycoder.kotlinnews.data.model

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val cause: Throwable, val reason: String? = null) : Result<T>()
}
