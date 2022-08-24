package com.gplay.core.domain.common.usecase

import com.gplay.core.domain.common.entity.Result

abstract class SimpleUseCase<P : Any, out T> {
    protected lateinit var param: P
        private set

    protected abstract suspend fun build(): T

    suspend operator fun invoke(param: P? = null): Result<T> {
        param?.run { this@SimpleUseCase.param = this }
        return try {
            Result.Success(build())
        } catch (error: Throwable) {
            Result.Failure(error)
        }
    }
}
