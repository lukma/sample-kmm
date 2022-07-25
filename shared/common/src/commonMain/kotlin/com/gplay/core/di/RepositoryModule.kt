package com.gplay.core.di

import org.koin.dsl.module

internal val repositoryModule = module {
    includes(dataSourceModule)
}
