package com.gplay.core.domain.validation.usecase

import com.gplay.core.domain.common.usecase.FlowUseCase
import com.gplay.core.domain.validation.FieldToValidate
import com.gplay.core.domain.validation.ValidationState
import com.gplay.core.domain.validation.ValidationStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FormValidationUseCase : FlowUseCase<FormValidationUseCase.Param, ValidationStates>() {

    override suspend fun build(): Flow<ValidationStates> {
        return flow {
            val current = param.current.toMutableMap()
            val (field, value) = param.toValidate

            var state: ValidationState = ValidationState.Valid
            for (rule in field.rules) {
                val error = rule.onValidate(value)
                if (error != null) {
                    state = ValidationState.Invalid(error)
                    break
                }
            }
            current[field] = state

            emit(current)
        }
    }

    data class Param(
        val toValidate: FieldToValidate,
        val current: ValidationStates,
    )
}
