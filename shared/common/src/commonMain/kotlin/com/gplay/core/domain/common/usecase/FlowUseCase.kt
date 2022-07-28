package com.gplay.core.domain.common.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class FlowUseCase<P : Any, out T> {
    protected lateinit var param: P
        private set

    protected abstract suspend fun build(): Flow<T>

    suspend operator fun invoke(param: P? = null): Flow<T> {
        param?.run { this@FlowUseCase.param = this }
        return runCatching {
            build()
        }.getOrElse {
            flow { throw it }
        }
    }
}
