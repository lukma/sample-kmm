package com.gplay.core.domain.common.entity

data class PagingParams<out T>(
    val key: T? = null,
    val pageSize: Int = defaultPagingSize,
) {

    companion object {
        const val defaultPagingSize = 10
    }
}
