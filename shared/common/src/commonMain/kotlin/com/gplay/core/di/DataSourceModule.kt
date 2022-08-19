package com.gplay.core.di

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.article.local.LocalArticleDataSource
import com.gplay.core.data.article.network.NetworkArticleDataSource
import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.auth.local.LocalAuthDataSource
import com.gplay.core.data.auth.network.NetworkAuthDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dataSourceModule = module {
    // Auth
    factory<AuthDataSource>(named(DataSourceQualifier.Local)) {
        LocalAuthDataSource(get())
    }
    factory<AuthDataSource>(named(DataSourceQualifier.Network)) {
        NetworkAuthDataSource(get(named(HttpClientQualifier.Simple)))
    }

    // Article
    factory<ArticleDataSource>(named(DataSourceQualifier.Local)) {
        LocalArticleDataSource(get())
    }
    factory<ArticleDataSource>(named(DataSourceQualifier.Network)) {
        NetworkArticleDataSource(get(named(HttpClientQualifier.Simple)))
    }
}
