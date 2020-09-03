package com.cheesycoder.kotlinnews.domain.controller

private val POSSIBLE_PICTURE_EXTENSION = listOf(
    ".jpeg",
    ".jpg",
    ".png",
    ".svg",
    ".tiff"
)

class ImageExtensionDetector {
    fun isEndingWithPictureExtension(url: String?): Boolean {
        if (url == null) return false
        for (extension in POSSIBLE_PICTURE_EXTENSION) {
            if (url.endsWith(extension, true)) {
                return true
            }
        }
        return false
    }
}
