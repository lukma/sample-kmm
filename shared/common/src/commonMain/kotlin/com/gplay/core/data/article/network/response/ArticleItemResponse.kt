package com.gplay.core.data.article.network.response

import com.gplay.core.domain.article.Article
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ArticleItemResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("created_at")
    val createdAt: Instant,
)

internal fun ArticleItemResponse.toArticle() = Article(
    id = id,
    title = title,
    content = content,
    thumbnail = thumbnail,
    createdAt = createdAt,
)
