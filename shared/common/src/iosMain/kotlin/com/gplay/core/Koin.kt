package com.gplay.core

import com.gplay.core.config.AppConfig
import com.gplay.core.domain.article.usecase.GetArticlesUseCase
import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun initKoin(appConfig: AppConfig) {
    initKoin(appConfig) {}
}

object CommonDependencies : KoinComponent {
    // Auth
    val signedInUseCase: SignInUseCase by inject()
    val isSignedInUseCase: IsSignedInUseCase by inject()

    // Article
    val getArticlesUseCase: GetArticlesUseCase by inject()

    // Validation
    val formValidationUseCase: FormValidationUseCase by inject()
}
