package com.gplay.core.domain.validation

sealed class FieldRule(
    val onValidate: (Any) -> ValidationError?,
) {

    object NoFieldBlank : FieldRule(onValidate = {
        val value = when (it) {
            is String -> it
            is Number -> runCatching { it.toString() }.getOrElse { "" }
            else -> ""
        }
        if (value.isBlank()) ValidationError.FieldBlank
        else null
    })
}
