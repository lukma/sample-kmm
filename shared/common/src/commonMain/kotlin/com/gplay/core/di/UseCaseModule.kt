package com.gplay.core.di

import com.gplay.core.domain.auth.usecase.SignInUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    includes(repositoryModule)

    // Auth
    factoryOf(::SignInUseCase)
}
