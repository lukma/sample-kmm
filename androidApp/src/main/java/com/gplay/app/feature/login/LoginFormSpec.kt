package com.gplay.app.feature.login

import com.gplay.core.domain.validation.FieldRule
import com.gplay.core.domain.validation.FieldSpec

enum class LoginFormSpec(override val rules: List<FieldRule>) : FieldSpec {
    Username(rules = listOf(FieldRule.NoFieldBlank)),
    Password(rules = listOf(FieldRule.NoFieldBlank));
}
