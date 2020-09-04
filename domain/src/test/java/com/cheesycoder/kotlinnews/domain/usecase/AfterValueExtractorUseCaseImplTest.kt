package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AfterValueExtractorUseCaseImplTest {

    private var useCase: AfterValueExtractorUseCase? = null
    private var testingList: MutableList<RedditArticleListItem>? = null

    @Before
    fun setUp() {
        useCase = AfterValueExtractorUseCaseImpl()
        testingList = mutableListOf()
    }

    @Test
    fun `extract valid after value - loadMore`() {
        val afterValue = "ansjkdhakl"
        givenValidAfterValueOnLoadMore(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract null after value - loadMore`() {
        val afterValue = null
        givenValidAfterValueOnLoadMore(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract empty after value - loadMore`() {
        val afterValue = ""
        givenValidAfterValueOnLoadMore(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract blank after value - loadMore`() {
        val afterValue = "   "
        givenValidAfterValueOnLoadMore(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract valid after value - try again`() {
        val afterValue = "ansjkdhakl"
        givenValidAfterValueOnTryAgain(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract null after value - try again`() {
        val afterValue = null
        givenValidAfterValueOnTryAgain(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract empty after value - try again`() {
        val afterValue = ""
        givenValidAfterValueOnTryAgain(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    @Test
    fun `extract blank after value - try again`() {
        val afterValue = "   "
        givenValidAfterValueOnTryAgain(afterValue)

        val extracted = useCase!!.extract(testingList!!)

        Assert.assertEquals(afterValue, extracted)
    }

    private fun givenValidAfterValueOnLoadMore(afterValue: String?) {
        testingList!!.add(mockk())
        testingList!!.add(RedditArticleListItem.LoadMore(afterValue))
    }

    private fun givenValidAfterValueOnTryAgain(afterValue: String?) {
        testingList!!.add(mockk())
        testingList!!.add(RedditArticleListItem.TryAgain(afterValue))
    }

    @After
    fun tearDown() {
        useCase = null
        testingList = null
    }
}
