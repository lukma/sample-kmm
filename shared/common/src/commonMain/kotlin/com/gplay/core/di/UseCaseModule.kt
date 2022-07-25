package com.gplay.core.di

import org.koin.dsl.module

internal val useCaseModule = module {
    includes(repositoryModule)
}
