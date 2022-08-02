package com.gplay.app.ui

import androidx.compose.runtime.compositionLocalOf

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    noLocalProvidedFor("ScaffoldController")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
