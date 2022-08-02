package com.gplay.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun GPlayScaffold(
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scaffoldController = remember { SimpleScaffoldController(scope, snackbarHostState) }

    CompositionLocalProvider(LocalScaffoldController provides scaffoldController) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = content,
        )
    }
}

interface ScaffoldController {
    fun showSnackbar(message: String?)
}

private class SimpleScaffoldController(
    private val scope: CoroutineScope,
    private val snackbarHostState: SnackbarHostState,
): ScaffoldController {

    override fun showSnackbar(message: String?) {
        scope.launch { snackbarHostState.showSnackbar(message.orEmpty()) }
    }
}
