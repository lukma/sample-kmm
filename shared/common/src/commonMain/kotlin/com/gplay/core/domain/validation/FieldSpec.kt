package com.gplay.core.domain.validation

interface FieldSpec {
    val key: String
    val rules: List<FieldRule>
}

fun FieldSpec.makeFieldToValidate(value: String) = FieldToValidate(
    key = key,
    rules = rules,
    value = value,
)
