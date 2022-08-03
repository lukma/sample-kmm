package com.gplay.core.di

import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    includes(repositoryModule)

    // Auth
    factoryOf(::SignInUseCase)
    factoryOf(::IsSignedInUseCase)

    // Validation
    factoryOf(::FormValidationUseCase)
}
