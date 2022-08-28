package com.gplay.core.data.article.local

import com.gplay.core.data.db.ArticleTable
import com.gplay.core.domain.article.Article
import kotlinx.datetime.toInstant

internal fun Article.toArticleTable() = ArticleTable(
    id = id,
    title = title,
    content = content,
    thumbnail = thumbnail,
    createdAt = createdAt.toString(),
)

internal fun mapToArticle(
    id: String,
    title: String,
    content: String,
    thumbnail: String,
    createdAt: String,
) = Article(
    id = id,
    title = title,
    content = content,
    thumbnail = thumbnail,
    createdAt = createdAt.toInstant(),
)
