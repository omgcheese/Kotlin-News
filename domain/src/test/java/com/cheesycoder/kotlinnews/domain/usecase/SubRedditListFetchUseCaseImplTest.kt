package com.cheesycoder.kotlinnews.domain.usecase

import com.cheesycoder.kotlinnews.common.model.Result
import com.cheesycoder.kotlinnews.data.repository.RedditRepository
import com.cheesycoder.kotlinnews.domain.controller.ArticleItemTransformer
import com.cheesycoder.kotlinnews.domain.controller.ArticleResultFlattener
import com.cheesycoder.kotlinnews.domain.model.RedditArticleListItem
import com.cheesycoder.kotlinnews.domain.util.DataFixture
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SubRedditListFetchUseCaseImplTest {

    private var usecase: SubRedditListFetchUseCase? = null
    private val repository: RedditRepository = mockk()
    private val flattener: ArticleResultFlattener = mockk()
    private val transformer: ArticleItemTransformer = mockk()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        usecase = SubRedditListFetchUseCaseImpl(
            repository,
            flattener,
            transformer,
            testCoroutineDispatcher
        )
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `execute - preserve error response`() = testCoroutineDispatcher.runBlockingTest {
        val mockException = RuntimeException()
        val mockReason = "Testing out reasoning"
        coEvery { repository.getArticlesOf(any(), any()) } returns Result.Error(
            mockException,
            mockReason
        )
        every { flattener.convert(any()) } returns DataFixture.createResultError(
            cause = mockException,
            reason = mockReason
        )
        every { transformer.transform(any()) } returns Result.Error(
            mockException,
            mockReason
        )

        val errorResult = usecase!!.execute("fake kotlin", null)

        errorResult as Result.Error
        Assert.assertEquals(errorResult.cause, mockException)
        Assert.assertEquals(errorResult.reason, mockReason)
    }

    @Test
    fun `execute - convert to domain model`() = testCoroutineDispatcher.runBlockingTest {
        val mockRedditArticleListItem: RedditArticleListItem = mockk()
        coEvery { repository.getArticlesOf(any(), any()) } returns Result.Success(mockk())
        every { flattener.convert(any()) } returns DataFixture.createResultSuccess(mockk())
        every { transformer.transform(any()) } returns Result.Success(
            listOf(
                mockRedditArticleListItem
            )
        )

        val successResult = usecase!!.execute("fake kotlin", null)

        successResult as Result.Success
        Assert.assertEquals(successResult.data.first(), mockRedditArticleListItem)
    }
}
