package com.gplay.core.data.article.local

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import com.gplay.core.domain.common.entity.asLocalLimitAndOffset
import com.gplay.core.domain.common.exception.APIError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class LocalArticleDataSource(private val database: AppDatabase) : ArticleDataSource {
    override suspend fun storeArticles(articles: List<Article>) {
        try {
            database.transaction {
                articles.forEach {
                    database.articleQueries.upsert(article = it.toLocalArticle())
                }
            }
        } catch (ex: Exception) {
            throw APIError.DatabaseFailure(ex)
        }
    }

    override suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>> {
        return flow {
            val (limit, offset) = paging.asLocalLimitAndOffset()
            val articles = database.articleQueries
                .finds(limit, offset, ::mapToArticle)
                .executeAsList()
            val result = PagingResult.Local(items = articles)
            emit(result)
        }.catch { throw APIError.DatabaseFailure(it) }
    }
}
