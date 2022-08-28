package com.gplay.core.data.article.network

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.article.network.response.ArticleItemResponse
import com.gplay.core.data.article.network.response.toArticle
import com.gplay.core.data.common.network.response.PagingResponse
import com.gplay.core.data.common.network.response.toPagingResult
import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetworkArticleDataSource(private val httpClient: HttpClient) : ArticleDataSource {
    override suspend fun storeArticles(articles: List<Article>) {
        throw NotImplementedError()
    }

    override suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>> {
        return flow {
            val resource = ArticleResource.Paging(
                page = paging.key ?: 1,
                pageSize = paging.pageSize,
            )
            val response = httpClient.get(resource)
                .body<PagingResponse<ArticleItemResponse>>()
                .toPagingResult { it.toArticle() }
            emit(response)
        }
    }
}
