package com.gplay.android.uikit.util.validation

import android.content.Context
import com.gplay.android.uikit.R
import com.gplay.core.domain.validation.ValidationError

fun ValidationError.message(context: Context, vararg formatArgs: Any): String {
    val resourceId = when (this) {
        is ValidationError.FieldBlank -> R.string.error_textfield_blank
    }

    val text = if (formatArgs.isEmpty()) {
        context.getString(resourceId)
    } else {
        context.getString(resourceId, *formatArgs)
    }
    return text
}
