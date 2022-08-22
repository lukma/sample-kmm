package com.gplay.compose.uikit.util.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.visibilityBy(isVisible: () -> Boolean): Modifier = alpha(if (isVisible()) 1f else 0f)
