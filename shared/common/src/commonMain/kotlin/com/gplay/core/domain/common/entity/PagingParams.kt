package com.gplay.core.domain.common.entity

data class PagingParams<out T>(
    val key: T? = null,
    val pageSize: Int = defaultPagingSize,
) {

    companion object {
        const val defaultPagingSize = 10
    }
}

internal fun PagingParams<Int>.asLocalLimitAndOffset(): Pair<Long, Long> {
    val key = key?.toLong() ?: 0L
    val offset = key * pageSize
    val limit = offset + pageSize
    return Pair(limit, offset)
}
