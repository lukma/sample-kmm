package com.gplay.app.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.paging.compose.collectAsLazyPagingItems
import com.gplay.android.navigation.ScreenNavGraph
import org.koin.androidx.compose.getViewModel

object HomeScreen : ScreenNavGraph {
    override val route: String = "home"
    override fun content(): @Composable (NavBackStackEntry) -> Unit = {
        val viewModel: HomeViewModel = getViewModel()
        val pagingItems = viewModel.paging.collectAsLazyPagingItems()
        HomeView(
            pagingItems = pagingItems,
        )
    }
}
