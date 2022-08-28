package com.gplay.core.domain.validation

sealed class ValidationError {
    object FieldBlank : ValidationError()
}
