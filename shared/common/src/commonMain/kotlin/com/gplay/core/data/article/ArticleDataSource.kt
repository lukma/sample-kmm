package com.gplay.core.data.article

import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.coroutines.flow.Flow

internal interface ArticleDataSource {
    suspend fun storeArticles(articles: List<Article>)
    suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>>
}
