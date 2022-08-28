package com.gplay.core.data.article

import com.gplay.core.domain.article.ArticleRepository
import com.gplay.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ArticleRepositoryImplTest {
    private val localArticleDataSource: ArticleDataSource = mockk()
    private val networkArticleDataSource: ArticleDataSource = mockk()
    private val articleRepository: ArticleRepository = ArticleRepositoryImpl(
        localArticleDataSource,
        networkArticleDataSource,
    )

    @Test
    fun `perform get articles when network reachable got value`() = runTest {
        // given
        coJustRun { localArticleDataSource.storeArticles(any()) }
        coEvery {
            networkArticleDataSource.getArticles(any())
        } returns flow { emit(TestSamples.articlePagingFromNetwork) }

        // when
        val actual = articleRepository.getArticles(
            paging = TestSamples.pagingParams,
        ).single()

        // then
        val expected = TestSamples.articlePagingFromNetwork
        assertEquals(expected, actual)
        coVerify {
            networkArticleDataSource.getArticles(paging = TestSamples.pagingParams)
            localArticleDataSource.storeArticles(articles = TestSamples.articles)
        }
    }

    @Test
    fun `perform get articles when network unreachable and any local stored got value`() = runTest {
        // given
        val networkError = Error()
        coEvery {
            localArticleDataSource.getArticles(any())
        } returns flow { emit(TestSamples.articlePagingFromLocal) }
        coEvery { networkArticleDataSource.getArticles(any()) } returns flow { throw networkError }

        // when
        val actual = articleRepository.getArticles(
            paging = TestSamples.pagingParams,
        ).single()

        // then
        val expected = TestSamples.articlePagingFromLocal.copy(prevError = networkError)
        assertEquals(expected, actual)
        coVerify { localArticleDataSource.getArticles(paging = TestSamples.pagingParams) }
    }

    @Test
    fun `perform get articles got failure`() = runTest {
        // given
        coEvery { localArticleDataSource.getArticles(any()) } returns flow { throw Error() }
        coEvery { networkArticleDataSource.getArticles(any()) } returns flow { throw Error() }

        // when
        val actual = runCatching {
            articleRepository.getArticles(
                paging = TestSamples.pagingParams,
            ).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
