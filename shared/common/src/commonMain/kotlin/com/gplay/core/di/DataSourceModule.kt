package com.gplay.core.di

import org.koin.dsl.module

internal val dataSourceModule = module {
    includes(databaseModule, networkModule)
}
