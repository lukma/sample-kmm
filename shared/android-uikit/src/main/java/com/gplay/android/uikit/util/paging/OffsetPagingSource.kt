package com.gplay.android.uikit.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class OffsetPagingSource<T : Any>(
    private val onLoad: (LoadParams<Int>) -> List<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return runCatching {
            val position = params.key ?: 0
            val data = onLoad(params)
            val prevKey = if (position == 0) null else position - 1
            val nextKey = if (data.isEmpty()) null else position + 1
            LoadResult.Page(data, prevKey, nextKey)
        }.getOrElse {
            LoadResult.Error(it)
        }
    }
}
