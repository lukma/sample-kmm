package com.gplay.core.util

import com.gplay.core.domain.article.Article
import com.gplay.core.domain.auth.Token
import com.gplay.core.domain.common.entity.PagingParams
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.datetime.Instant

object TestSamples {
    // Common
    val pagingParams = PagingParams<Int>()

    // Auth
    const val tokenString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG4uZG9lQG1haWwuY29tIiwiZnVsbG5hbWUiOiJKb2huIERvZSJ9.z5D0sySgc4zqI0zL1dakHVMYaYrPPtoAFQvZGKL7kTg"
    val token = Token(
        accessToken = tokenString,
        refreshToken = tokenString,
        tokenType = "Bearer",
        expiresIn = Instant.parse("2022-12-31T12:00:00.000124Z"),
    )
    const val username = "john.doe@mail.com"
    const val password = "qwerty"

    // Article
    val articles = listOf(
        Article(
            id = "1",
            title = "Lorem ipsum",
            content = "Lorem ipsum dolor sit amet",
            thumbnail = "https://picsum.photos/id/1/300",
            createdAt = Instant.parse("2022-12-31T12:00:00.000124Z"),
        ),
    )
    val articlePagingFromLocal = PagingResult.Local(items = articles)
    val articlePagingFromNetwork = PagingResult.Network(items = articles, total = articles.size)
}
