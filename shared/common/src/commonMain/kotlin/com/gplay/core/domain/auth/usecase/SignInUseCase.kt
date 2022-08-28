package com.gplay.core.domain.auth.usecase

import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.domain.common.usecase.SimpleUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single

class SignInUseCase internal constructor(
    private val authRepository: AuthRepository,
) : SimpleUseCase<SignInUseCase.Param, Unit>() {

    override suspend fun build() {
        authRepository.signIn(
            username = param.username,
            password = param.password,
        )
            .onEach { authRepository.storeToken(username = param.username, token = it) }
            .single()
    }

    data class Param(
        val username: String,
        val password: String,
    )
}
