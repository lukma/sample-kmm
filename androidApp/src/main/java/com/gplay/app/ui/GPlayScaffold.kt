package com.gplay.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun GPlayScaffold(
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldController = remember { SimpleScaffoldController(snackbarHostState) }

    CompositionLocalProvider(LocalScaffoldController provides scaffoldController) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = content,
        )
    }
}

interface ScaffoldController {
    suspend fun showSnackbar(message: String?): SnackbarResult
}

private class SimpleScaffoldController(
    private val snackbarHostState: SnackbarHostState,
) : ScaffoldController {

    override suspend fun showSnackbar(message: String?): SnackbarResult {
       return snackbarHostState.showSnackbar(message.orEmpty())
    }
}
