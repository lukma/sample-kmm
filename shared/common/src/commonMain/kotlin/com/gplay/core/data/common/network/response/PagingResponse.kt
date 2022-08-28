package com.gplay.core.data.common.network.response

import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PagingResponse<T>(
    @SerialName("items")
    val items: List<T>,
    @SerialName("total")
    val total: Int,
)

internal fun <I, O> PagingResponse<I>.toPagingResult(mapper: (I) -> O): PagingResult<O> {
    return PagingResult.Network(
        items = items.map(mapper),
        total = total,
    )
}
