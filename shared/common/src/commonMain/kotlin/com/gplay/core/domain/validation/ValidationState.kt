package com.gplay.core.domain.validation

sealed class ValidationState {
    object Valid : ValidationState()
    data class Invalid(val error: ValidationError) : ValidationState()
}

typealias ValidationStates = Map<String, ValidationState?>

fun ValidationStates.isAllValid(): Boolean {
    if (isEmpty()) return false

    forEach { (_, value) ->
        if (value == null || value is ValidationState.Invalid) {
            return false
        }
    }
    return true
}
