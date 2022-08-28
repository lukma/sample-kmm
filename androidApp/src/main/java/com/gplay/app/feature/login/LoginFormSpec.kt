package com.gplay.app.feature.login

import com.gplay.core.domain.validation.FieldRule
import com.gplay.core.domain.validation.FieldSpec

enum class LoginFormSpec(
    override val key: String,
    override val rules: List<FieldRule>,
) : FieldSpec {

    Username(
        key = "username",
        rules = listOf(FieldRule.NoFieldBlank),
    ),
    Password(
        key = "password",
        rules = listOf(FieldRule.NoFieldBlank),
    );
}
