package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SubRedditListMergerUseCaseImplTest {

    private var useCase: SubRedditListMergerUseCase? = null
    private var oldList: MutableList<RedditArticleListItem>? = null
    private var newList: MutableList<RedditArticleListItem>? = null

    @Before
    fun setUp() {
        useCase = SubRedditListMergerUseCaseImpl()
        oldList = mutableListOf()
        newList = mutableListOf()
    }

    @Test
    fun `mergeOnSuccess - loadMore`() {
        givenOldListHasOneItemWithLoadMore()
        givenNewListHasOneNewItem()

        val merged = useCase!!.mergeOnSuccess(newList!!, oldList!!)

        Assert.assertEquals(merged.size, 2)
    }

    @Test
    fun `mergeOnSuccess - tryAgain`() {
        givenOldListHasOneItemWithTryAgain()
        givenNewListHasOneNewItem()

        val merged = useCase!!.mergeOnSuccess(newList!!, oldList!!)

        Assert.assertEquals(merged.size, 2)
    }

    @Test
    fun `mergeOnFailure - existing After is inserted back`() {
        val existingAfter = "jakdhailhdjlqw"
        givenOldListHasOneItemWithTryAgain(existingAfter)

        val merged = useCase!!.mergeOnFailure(oldList!!)

        val lastItem = merged.last() as RedditArticleListItem.TryAgain
        Assert.assertEquals(existingAfter, lastItem.after)
    }

    private fun givenOldListHasOneItemWithLoadMore() {
        oldList!!.add(mockk())
        oldList!!.add(RedditArticleListItem.LoadMore("test after"))
    }

    private fun givenOldListHasOneItemWithTryAgain(afterValue: String? = "test After") {
        oldList!!.add(mockk())
        oldList!!.add(RedditArticleListItem.TryAgain(afterValue))
    }

    private fun givenNewListHasOneNewItem() {
        newList!!.add(mockk())
    }

    @After
    fun tearDown() {
        useCase = null
        oldList = null
        newList = null
    }
}
