package com.gplay.compose.uikit.util.validation

import android.content.Context
import com.gplay.compose.uikit.R
import com.gplay.core.domain.validation.ValidationError
import com.gplay.core.domain.validation.ValidationState

fun ValidationState.Invalid.errorMessage(context: Context, vararg formatArgs: Any): String {
    val resourceId = when (this.error) {
        is ValidationError.FieldBlank -> R.string.error_textfield_blank
    }

    val text = if (formatArgs.isEmpty()) {
        context.getString(resourceId)
    } else {
        context.getString(resourceId, *formatArgs)
    }
    return text
}
