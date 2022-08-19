package com.gplay.core

import com.gplay.core.config.AppConfig
import com.gplay.core.di.databaseModule
import com.gplay.core.di.networkModule
import com.gplay.core.di.platformModule
import com.gplay.core.di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    appConfig: AppConfig,
    appDeclaration: KoinAppDeclaration,
) = startKoin {
    appDeclaration()
    modules(
        listOf(
            module { single { appConfig } },
            platformModule,
            databaseModule,
            networkModule,
            useCaseModule,
        )
    )
}
