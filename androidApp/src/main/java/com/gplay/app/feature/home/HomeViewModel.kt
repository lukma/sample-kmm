package com.gplay.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gplay.android.uikit.util.paging.OffsetPagingSource
import com.gplay.core.domain.article.usecase.GetArticlesUseCase
import com.gplay.core.domain.common.entity.PagingParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single

class HomeViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {

    val paging: Flow<PagingData<HomeListItemModel>> = Pager(
        PagingConfig(
            pageSize = PagingParams.defaultPagingSize,
            initialLoadSize = PagingParams.defaultPagingSize,
        ),
    ) {
        OffsetPagingSource(::getArticles)
    }
        .flow
        .cachedIn(viewModelScope)

    private suspend fun getArticles(key: Int, pageSize: Int): List<HomeListItemModel> {
        val param = GetArticlesUseCase.Param(paging = PagingParams(key, pageSize))
        return getArticlesUseCase(param).single()
            .items
            .map(HomeListItemModel::ArticleItem)
    }
}
