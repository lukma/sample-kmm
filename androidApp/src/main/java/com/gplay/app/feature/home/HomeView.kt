package com.gplay.app.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gplay.app.ui.GPlayScaffold
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.app.util.TestSamples
import com.gplay.compose.uikit.ui.ErrorView
import com.gplay.core.domain.article.Article
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock

@Composable
fun HomeView(
    pagingItems: LazyPagingItems<HomeListItemModel>,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
    ) {
        items(pagingItems) {
            when (it) {
                is HomeListItemModel.ArticleItem -> ArticleItem(article = it.item)
                else -> { /* no-op */ }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        when (val state = pagingItems.loadState.source.refresh) {
            is LoadState.NotLoading -> { /* no-op */ }
            is LoadState.Loading -> if (pagingItems.itemCount == 0) {
                item {
                    ArticleItem(article = dummyArticle, isLoading = true)
                }
            }
            is LoadState.Error -> item {
                ErrorView(
                    error = state.error,
                    onRetry = { pagingItems.retry() },
                    modifier = Modifier.padding(all = 8.dp),
                )
            }
        }
    }
}

private val dummyArticle = Article("", "", "", "", Clock.System.now())

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayScaffold {
            val data = TestSamples.articlePagingResult()
                .items
                .map(HomeListItemModel::ArticleItem)
            val pagingFlow = flowOf(PagingData.from<HomeListItemModel>(data))
            val pagingItems = pagingFlow.collectAsLazyPagingItems()
            HomeView(
                pagingItems = pagingItems,
            )
        }
    }
}
