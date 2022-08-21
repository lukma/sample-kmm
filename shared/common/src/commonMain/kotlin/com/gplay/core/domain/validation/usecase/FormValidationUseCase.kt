package com.gplay.core.domain.validation.usecase

import com.gplay.core.domain.common.usecase.FlowUseCase
import com.gplay.core.domain.validation.FieldToValidate
import com.gplay.core.domain.validation.ValidationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FormValidationUseCase :
    FlowUseCase<FormValidationUseCase.Param, Map<String, ValidationState?>>() {

    override suspend fun build(): Flow<Map<String, ValidationState?>> {
        return flow {
            val current = param.current.toMutableMap()

            var state: ValidationState = ValidationState.Valid
            for (rule in param.toValidate.rules) {
                val error = rule.onValidate(param.toValidate.value)
                if (error != null) {
                    state = ValidationState.Invalid(error)
                    break
                }
            }
            current[param.toValidate.key] = state

            emit(current)
        }
    }

    data class Param(
        val toValidate: FieldToValidate,
        val current: Map<String, ValidationState?>,
    )
}
