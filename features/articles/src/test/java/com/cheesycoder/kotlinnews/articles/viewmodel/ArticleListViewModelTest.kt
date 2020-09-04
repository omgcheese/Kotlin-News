package com.cheesycoder.kotlinnews.articles.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.common.model.ViewEffects
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import com.cheesycoder.kotlinnews.domain.usecase.AfterValueExtractorUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListFetchUseCase
import com.cheesycoder.kotlinnews.domain.usecase.SubRedditListMergerUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticleListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private var viewmodel: ArticleListViewModel? = null
    private val fetchUseCase: SubRedditListFetchUseCase = mockk()
    private val mergerUseCase: SubRedditListMergerUseCase = mockk()
    private val extracterUsecase: AfterValueExtractorUseCase = mockk()
    private val testCorotuineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        viewmodel = ArticleListViewModel(
            fetchUseCase,
            mergerUseCase,
            extracterUsecase,
            testCorotuineDispatcher
        )
        Dispatchers.setMain(testCorotuineDispatcher)
    }

    @Test
    fun `start - success`() = testCorotuineDispatcher.runBlockingTest {
        val subredditName = "test sub reddit name"
        val mockedResult = listOf<RedditArticleListItem>(mockk())

        coEvery { fetchUseCase.execute(any(), any()) } returns Result.Success(mockedResult)

        viewmodel!!.start(subredditName)

        Assert.assertEquals(viewmodel!!.itemList.value, mockedResult)
        Assert.assertTrue(viewmodel!!.isLoadedInitially)
        coVerify { fetchUseCase.execute(subredditName, null) }
    }

    @Test
    fun `start - error`() = testCorotuineDispatcher.runBlockingTest {
        val subredditName = "test sub reddit name"
        val errorMessage = "I can't let you do that Jin"
        coEvery {
            fetchUseCase.execute(
                any(),
                any()
            )
        } returns Result.Error(IllegalArgumentException(), errorMessage)

        viewmodel!!.start(subredditName)

        Assert.assertNull(viewmodel!!.itemList.value)
        Assert.assertEquals(viewmodel!!.errorMessage.value!!.observeData, "$errorMessage(java.lang.IllegalArgumentException)")
    }

    @Test
    fun `loadNext - null after value`() = testCorotuineDispatcher.runBlockingTest {
        val subredditName = "test sub reddit name"
        val mockedResult = listOf<RedditArticleListItem>(mockk())
        coEvery { fetchUseCase.execute(any(), any()) } returns Result.Success(mockedResult)
        coEvery { extracterUsecase.extract(any()) } returns null

        viewmodel!!.start(subredditName)
        viewmodel!!.loadNext()

        Assert.assertEquals(viewmodel!!.errorMessage.value, ViewEffects("Cannot load next page!"))
    }

    @Test
    fun `loadNext - error on loading next batch`() = testCorotuineDispatcher.runBlockingTest {
        val subredditName = "test sub reddit name"
        val mockedResult = listOf<RedditArticleListItem>(mockk())
        val mockedFinalResult = listOf<RedditArticleListItem>(mockk())
        coEvery {
            fetchUseCase.execute(
                any(),
                any()
            )
        } returns Result.Success(mockedResult) andThen Result.Error(Exception())
        coEvery { extracterUsecase.extract(any()) } returns "asdnqwjkdqw"
        coEvery { mergerUseCase.mergeOnFailure(any()) } returns mockedFinalResult

        viewmodel!!.start(subredditName)
        viewmodel!!.loadNext()

        Assert.assertEquals(viewmodel!!.itemList.value, mockedFinalResult)
    }

    @Test
    fun `loadNext - success on loading next batch`() = testCorotuineDispatcher.runBlockingTest {
        val subredditName = "test sub reddit name"
        val mockedResult = listOf<RedditArticleListItem>(mockk())
        val mockedFinalResult = listOf<RedditArticleListItem>(mockk())
        coEvery {
            fetchUseCase.execute(
                any(),
                any()
            )
        } returns Result.Success(mockedResult) andThen Result.Success(
            mockk()
        )
        coEvery { extracterUsecase.extract(any()) } returns "asdnqwjkdqw"
        coEvery { mergerUseCase.mergeOnSuccess(any(), any()) } returns mockedFinalResult

        viewmodel!!.start(subredditName)
        viewmodel!!.loadNext()

        Assert.assertEquals(viewmodel!!.itemList.value, mockedFinalResult)
    }

    @After
    fun tearDown() {
        viewmodel = null
        Dispatchers.resetMain()
        testCorotuineDispatcher.cleanupTestCoroutines()
    }
}
