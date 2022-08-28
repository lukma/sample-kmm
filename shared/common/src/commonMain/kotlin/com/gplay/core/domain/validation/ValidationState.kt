package com.gplay.core.domain.validation

sealed class ValidationState {
    object None : ValidationState()
    object Valid : ValidationState()
    data class Invalid(val error: ValidationError) : ValidationState()
}

fun Map<String, ValidationState>.isAllValid(): Boolean {
    if (isEmpty()) return false
    return all { (_, value) -> value is ValidationState.Valid }
}
