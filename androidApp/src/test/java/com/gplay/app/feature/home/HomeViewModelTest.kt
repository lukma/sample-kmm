package com.gplay.app.feature.home

import com.gplay.app.util.CoroutinesTestRule
import com.gplay.app.util.TestSamples
import com.gplay.app.util.asPagingDataError
import com.gplay.app.util.asPagingDataItems
import com.gplay.core.domain.article.usecase.GetArticlesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class HomeViewModelTest {
    private val getArticlesUseCase: GetArticlesUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `perform get articles got value`() = runTest {
        // given
        coEvery { getArticlesUseCase(any()) } returns flow { emit(TestSamples.articlePagingResult()) }
        viewModel = HomeViewModel(getArticlesUseCase)

        // when
        val actual = viewModel.paging.asPagingDataItems(this)

        // then
        val expected = TestSamples.articlePagingResult()
            .items
            .map(HomeListItemModel::ArticleItem)
        assertEquals(expected, actual)
    }

    @Test
    fun `perform get articles got failure`() = runTest {
        // given
        val error = Error()
        coEvery { getArticlesUseCase(any()) } returns flow { throw error }
        viewModel = HomeViewModel(getArticlesUseCase)

        // when
        val actual = viewModel.paging.asPagingDataError(this)

        // then
        assertEquals(error, actual)
    }
}
