package com.gplay.android.uikit.util.paging

import androidx.paging.PagingSource
import com.gplay.core.domain.common.entity.PagingParams

fun PagingSource.LoadParams<Int>.toPagingParams() = PagingParams(
    key = key,
    pageSize = loadSize,
)
