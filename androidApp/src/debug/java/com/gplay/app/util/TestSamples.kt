package com.gplay.app.util

import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.datetime.Instant

object TestSamples {
    // Common
    val error = Error("some error")

    // Auth
    const val username = "john.doe@mail.com"
    const val password = "qwerty"

    // Article
    fun articlePagingResult(page: Int = 1): PagingResult<Article> {
        val offset = (page * 10) - 9
        val articles = (offset..offset + 10).map {
            Article(
                id = it.toString(),
                title = "Lorem ipsum",
                content = "Lorem ipsum dolor sit amet",
                thumbnail = "https://picsum.photos/id/1/300",
                createdAt = Instant.parse("2022-12-31T12:00:00.000124Z"),
            )
        }
        return PagingResult.Network(items = articles, total = articles.size)
    }
}
