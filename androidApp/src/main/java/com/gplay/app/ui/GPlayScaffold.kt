package com.gplay.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun GPlayScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldController = remember { ScaffoldControllerImpl(snackbarHostState) }

    CompositionLocalProvider(LocalScaffoldController provides scaffoldController) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = content
        )
    }
}

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    error("CompositionLocal ScaffoldController not present")
}

interface ScaffoldController {
    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ): SnackbarResult
}

private class ScaffoldControllerImpl(
    private val snackbarHostState: SnackbarHostState,
) : ScaffoldController {

    override suspend fun showSnackbar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
    ): SnackbarResult {
        return snackbarHostState.showSnackbar(message, actionLabel, withDismissAction, duration)
    }
}
