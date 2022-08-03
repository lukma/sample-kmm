package com.gplay.core.domain.validation

sealed class ValidationState {
    object Valid : ValidationState()
    data class Invalid(val error: ValidationError) : ValidationState()
}

typealias ValidationStates = Map<FieldSpec, ValidationState>

inline val ValidationState.isInValid get() = this is ValidationState.Invalid
