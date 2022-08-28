package com.gplay.app.feature.home

import com.gplay.core.domain.article.Article

sealed class HomeListItemModel {
    data class ArticleItem(val item: Article) : HomeListItemModel()
}
