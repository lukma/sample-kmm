package com.gplay.core.domain.common.entity

sealed class PagingResult<out T>(open val items: List<T>) {
    data class Local<T>(
        override val items: List<T>,
        val prevError: Throwable? = null,
    ) : PagingResult<T>(items)

    data class Network<T>(
        override val items: List<T>,
        val total: Int,
    ) : PagingResult<T>(items)
}

internal fun <T> PagingResult<T>.asLocalResultWithNetworkFail(error: Throwable): PagingResult<T> {
    return (this as PagingResult.Local).copy(prevError = error)
}
