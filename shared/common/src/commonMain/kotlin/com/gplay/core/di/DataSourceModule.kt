package com.gplay.core.di

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.auth.local.LocalAuthDataSource
import com.gplay.core.data.auth.network.NetworkAuthDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dataSourceModule = module {
    includes(databaseModule, networkModule)

    // Auth
    factory<AuthDataSource>(named(DataSourceQualifier.Local)) {
        LocalAuthDataSource(get())
    }
    factory<AuthDataSource>(named(DataSourceQualifier.Network)) {
        NetworkAuthDataSource(get(named(HttpClientQualifier.Simple)))
    }
}
