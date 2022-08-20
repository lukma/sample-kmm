package com.gplay.core.domain.validation.usecase

import com.gplay.core.domain.validation.*
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FormValidationUseCaseTest {
    private val useCase = FormValidationUseCase()

    @Test
    fun `perform validate field when receive valid value`() = runTest {
        // given
        val toValidate = SampleFormSpec.SomeField.makeFieldToValidate("ok")
        val current: ValidationStates = mapOf()

        // when
        val param = FormValidationUseCase.Param(toValidate, current)
        val actual = useCase(param).single()

        // then
        val expected: ValidationStates = mapOf(
            SampleFormSpec.SomeField.key to ValidationState.Valid,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `perform validate field when receive invalid value`() = runTest {
        // given
        val toValidate = SampleFormSpec.SomeField.makeFieldToValidate("")
        val current: ValidationStates = mapOf()

        // when
        val param = FormValidationUseCase.Param(toValidate, current)
        val actual = useCase(param).single()

        // then
        val expected: ValidationStates = mapOf(
            SampleFormSpec.SomeField.key to ValidationState.Invalid(ValidationError.FieldBlank),
        )
        assertEquals(expected, actual)
    }
}

enum class SampleFormSpec(override val rules: List<FieldRule>) : FieldSpec {
    SomeField(rules = listOf(FieldRule.NoFieldBlank));
}
