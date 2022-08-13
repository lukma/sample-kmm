package com.gplay.app.util

import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

suspend fun <T : Any> Flow<PagingData<T>>.asPagingDataItems(
    testScope: TestScope,
) = suspendCoroutine { continuation ->
    extractPagingData(
        coroutineContext = testScope.coroutineContext,
        extractWhen = { it.refresh is LoadState.NotLoading && it.append is LoadState.NotLoading },
        extractState = { differ, _ ->
            val items = differ.snapshot().items
            continuation.resumeWith(Result.success(items))
        },
    )
}

suspend fun <T : Any> Flow<PagingData<T>>.asPagingDataError(
    testScope: TestScope,
) = suspendCoroutine { continuation ->
    extractPagingData(
        coroutineContext = testScope.coroutineContext,
        extractWhen = { it.refresh is LoadState.Error },
        extractState = { _, state ->
            val error = (state.refresh as LoadState.Error).error
            continuation.resumeWith(Result.success(error))
        },
    )
}

fun <T : Any> Flow<PagingData<T>>.extractPagingData(
    coroutineContext: CoroutineContext,
    extractWhen: (CombinedLoadStates) -> Boolean,
    extractState: (PagingDataDiffer<T>, CombinedLoadStates) -> Unit,
) {
    val differ = PagingDataDifferTest<T>()
    val differJob = CoroutineScope(coroutineContext).launch {
        this@extractPagingData.collect(differ::collectFrom)
    }
    differ.addLoadStateListener {
        if (extractWhen(it)) {
            extractState(differ, it)
            differJob.cancel()
        }
    }
}

private class PagingDataDifferTest<T : Any> : PagingDataDiffer<T>(
    differCallback = NoopListCallback,
) {

    override suspend fun presentNewList(
        previousList: NullPaddedList<T>,
        newList: NullPaddedList<T>,
        lastAccessedIndex: Int,
        onListPresentable: () -> Unit
    ): Int? {
        onListPresentable()
        return null
    }
}

private object NoopListCallback : DifferCallback {
    override fun onChanged(position: Int, count: Int) { /* no-op */ }
    override fun onInserted(position: Int, count: Int) { /* no-op */ }
    override fun onRemoved(position: Int, count: Int) { /* no-op */ }
}
