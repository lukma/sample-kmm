package com.gplay.core.domain.auth.usecase

import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.domain.common.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow

class IsSignedInUseCase internal constructor(
    private val authRepository: AuthRepository,
) : FlowUseCase<Nothing, Boolean>() {

    override suspend fun build(): Flow<Boolean> {
        return authRepository.isSignedIn()
    }
}
