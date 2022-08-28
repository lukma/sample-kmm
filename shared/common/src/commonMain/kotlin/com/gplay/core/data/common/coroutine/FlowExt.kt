package com.gplay.core.data.common.coroutine

import com.gplay.core.domain.common.entity.PagingResult
import com.gplay.core.domain.common.entity.asLocalResultWithNetworkFail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single

internal fun <T> Flow<PagingResult<T>>.addLocalDataSourceStrategy(
    storeToLocal: suspend (PagingResult<T>) -> Unit,
    retrieveFromLocal: suspend () -> Flow<PagingResult<T>>,
): Flow<PagingResult<T>> = onEach(storeToLocal)
    .catch { networkError ->
        val items = runCatching { retrieveFromLocal().single() }
            .getOrElse { throw networkError }
            .asLocalResultWithNetworkFail(networkError)
        emit(items)
    }
