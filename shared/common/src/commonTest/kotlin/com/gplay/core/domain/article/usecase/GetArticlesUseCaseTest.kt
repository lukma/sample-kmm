package com.gplay.core.domain.article.usecase

import com.gplay.core.domain.article.ArticleRepository
import com.gplay.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GetArticlesUseCaseTest {
    private val articleRepository: ArticleRepository = mockk()
    private val useCase = GetArticlesUseCase(articleRepository)
    private val useCaseParam = GetArticlesUseCase.Param(
        paging = TestSamples.pagingParams,
    )

    @Test
    fun `perform get articles got value`() = runTest {
        // given
        coEvery {
            articleRepository.getArticles(any())
        } returns flow { emit(TestSamples.articlePagingFromNetwork) }

        // when
        val actual = useCase(useCaseParam).single()

        // then
        val expected = TestSamples.articlePagingFromNetwork
        assertEquals(expected, actual)
        coVerify { articleRepository.getArticles(paging = TestSamples.pagingParams) }
    }

    @Test
    fun `perform get articles got failure`() = runTest {
        // given
        coEvery { articleRepository.getArticles(any()) } returns flow { throw Error() }

        // when
        val actual = runCatching {
            useCase(useCaseParam).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
