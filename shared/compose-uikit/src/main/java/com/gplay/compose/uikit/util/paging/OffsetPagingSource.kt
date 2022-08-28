package com.gplay.compose.uikit.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class OffsetPagingSource<T : Any>(
    private val onLoad: suspend (key: Int, pageSize: Int) -> List<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return runCatching {
            val requestPage = params.key ?: initialPage
            val data = onLoad(requestPage, params.loadSize)
            val nextPage = if (data.isEmpty()) null else requestPage + 1
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage,
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    companion object {
        private const val initialPage = 1
    }
}
