package com.gplay.app.feature.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.text.HtmlCompat
import androidx.paging.compose.collectAsLazyPagingItems
import com.gplay.app.ui.GPlayScaffold
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.app.util.TestSamples
import com.gplay.core.domain.article.usecase.GetArticlesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

class HomeViewTest {
    private val getArticlesUseCase: GetArticlesUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchView() {
        viewModel = HomeViewModel(getArticlesUseCase)
        composeTestRule.setContent {
            GPlayTheme {
                GPlayScaffold {
                    val pagingItems = viewModel.paging.collectAsLazyPagingItems()
                    HomeView(
                        pagingItems = pagingItems,
                    )
                }
            }
        }
    }

    @Test
    fun perform_get_articles_got_value() {
        // given
        coEvery { getArticlesUseCase(any()) } returns flow { emit(TestSamples.articlePagingResult()) }

        // when
        launchView()

        // then
        val parentMatcher = hasParent(hasTestTag("ArticleItem"))
        TestSamples.articlePagingResult().items.subList(0, 1).forEach {
            composeTestRule.onNode(parentMatcher and hasText(it.title))
                .assertIsDisplayed()
            val plainContent = HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
            composeTestRule.onNode(parentMatcher and hasTextExactly(plainContent))
                .assertIsDisplayed()
        }
    }

    @Test
    fun perform_get_articles_got_failure() {
        // given
        coEvery { getArticlesUseCase(any()) } returns flow { throw TestSamples.error }

        // when
        launchView()

        // then
        val parentMatcher = hasParent(hasTestTag("ErrorView"))
        composeTestRule.onNode(parentMatcher and hasText(TestSamples.error.message.orEmpty()))
            .assertIsDisplayed()
    }
}
