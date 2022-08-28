package com.gplay.core.domain.common.entity

sealed class Result<out V> {
    data class Success<out V>(val value: V) : Result<V>()
    data class Failure<out V>(val error: Throwable) : Result<V>()
}

fun <V> Result<V>.onSuccess(block: (V) -> Unit) = apply {
    if (this is Result.Success) {
        block(value)
    }
}

fun <V> Result<V>.onFailure(block: (Throwable) -> Unit) = apply {
    if (this is Result.Failure) {
        block(error)
    }
}
