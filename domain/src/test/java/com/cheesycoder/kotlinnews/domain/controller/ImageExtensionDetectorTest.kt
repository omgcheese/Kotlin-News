package com.cheesycoder.kotlinnews.domain.controller

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ImageExtensionDetectorTest {

    private var detector: ImageExtensionDetector? = null

    @Before
    fun setUp() {
        detector = ImageExtensionDetector()
    }

    @After
    fun tearDown() {
        detector = null
    }

    @Test
    fun `verify png`() {
        val result = detector!!.isEndingWithPictureExtension("www.this.is.photo.png")

        assert(result)
    }

    @Test
    fun `verify PNG`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.PNG")

        assert(result)
    }

    @Test
    fun `verify jpeg`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.jpeg")

        assert(result)
    }

    @Test
    fun `verify jpg`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.jpg")

        assert(result)
    }

    @Test
    fun `verify JPG`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.JPG")

        assert(result)
    }

    @Test
    fun `verify JPEG`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.JPEG")

        assert(result)
    }

    @Test
    fun `verify tiff`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.tiff")

        assert(result)
    }

    @Test
    fun `verify svg`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.svg")

        assert(result)
    }

    @Test
    fun `verify SVG`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.SVG")

        assert(result)
    }

    @Test
    fun `verify gif is not supported`() {
        val result = detector!!.isEndingWithPictureExtension("this.is.photo.gif")

        Assert.assertFalse(result)
    }

    @Test
    fun `verify normal url is not supported`() {
        val result = detector!!.isEndingWithPictureExtension("www.google.com")

        Assert.assertFalse(result)
    }
}
