package com.gplay.core.data.article.network

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow

internal class NetworkArticleDataSource(private val httpClient: HttpClient) : ArticleDataSource {
    override suspend fun storeArticles(articles: List<Article>) {
        throw NotImplementedError()
    }

    override suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>> {
        throw NotImplementedError()
    }
}
