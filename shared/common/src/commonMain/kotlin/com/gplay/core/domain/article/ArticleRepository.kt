package com.gplay.core.domain.article

import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.coroutines.flow.Flow

internal interface ArticleRepository {
    suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>>
}
