package com.gplay.core.domain.article

import kotlinx.datetime.Instant

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val thumbnail: String,
    val createdAt: Instant,
)
