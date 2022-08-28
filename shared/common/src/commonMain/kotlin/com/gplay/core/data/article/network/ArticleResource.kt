package com.gplay.core.data.article.network

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/v1/articles")
internal class ArticleResource{

    @Serializable
    @Resource("")
    class Paging(
        val parent: ArticleResource = ArticleResource(),
        val page: Int,
        val pageSize: Int,
    )
}
