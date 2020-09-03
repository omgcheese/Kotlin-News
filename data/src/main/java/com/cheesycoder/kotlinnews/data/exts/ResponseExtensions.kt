package com.cheesycoder.kotlinnews.data.exts

import com.cheesycoder.kotlinnews.common.model.Result
import retrofit2.Response

private const val EMPTY_BODY_ERROR = "payload is empty. Please check the response definition."
private const val NOT_SUCCESSFUL = "api request was not successful."

internal fun <T> Response<T>.toResult(): Result<T> {
    return if (this.isSuccessful) {
        val data = this.body()
        if (data == null) {
            Result.Error(IllegalArgumentException(), EMPTY_BODY_ERROR)
        } else {
            Result.Success(data)
        }
    } else {
        Result.Error(IllegalStateException(), NOT_SUCCESSFUL)
    }
}
