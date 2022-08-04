package com.gplay.core.data.article

import com.gplay.core.data.common.coroutine.addLocalDataSourceStrategy
import com.gplay.core.domain.article.Article
import com.gplay.core.domain.article.ArticleRepository
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.coroutines.flow.Flow

internal class ArticleRepositoryImpl(
    private val localArticleDataSource: ArticleDataSource,
    private val networkArticleDataSource: ArticleDataSource,
) : ArticleRepository {

    override suspend fun getArticles(paging: PagingParams<Int>): Flow<PagingResult<Article>> {
        return networkArticleDataSource.getArticles(paging)
            .addLocalDataSourceStrategy(
                storeToLocal = { localArticleDataSource.storeArticles(it.items) },
                retrieveFromLocal = { localArticleDataSource.getArticles(paging) },
            )
    }
}
