package com.gplay.core.di

import com.gplay.core.data.auth.AuthRepositoryImpl
import com.gplay.core.domain.auth.AuthRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val repositoryModule = module {
    includes(dataSourceModule)

    single<AuthRepository> {
        AuthRepositoryImpl(
            get(named(DataSourceQualifier.Local)),
            get(named(DataSourceQualifier.Network)),
        )
    }
}
