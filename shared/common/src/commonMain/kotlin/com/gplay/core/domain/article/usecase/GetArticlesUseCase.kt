package com.gplay.core.domain.article.usecase

import com.gplay.core.domain.article.Article
import com.gplay.core.domain.article.ArticleRepository
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import com.gplay.core.domain.common.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase internal constructor(
    private val articleRepository: ArticleRepository,
) : FlowUseCase<GetArticlesUseCase.Param, PagingResult<Article>>() {

    override suspend fun build(): Flow<PagingResult<Article>> {
        return articleRepository.getArticles(
            paging = param.paging,
        )
    }

    data class Param(
        val paging: PagingParams<Int>,
    )
}
