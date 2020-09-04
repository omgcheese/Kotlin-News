package com.cheesycoder.kotlinnews.common.model

data class ViewEffects<T>(
    private val data: T
) {
    private var isObserved = false

    val observeData: T?
        get() {
            return if (isObserved) {
                null
            } else {
                isObserved = true
                data
            }
        }
}