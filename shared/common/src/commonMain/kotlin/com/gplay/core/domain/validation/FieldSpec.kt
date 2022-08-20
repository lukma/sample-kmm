package com.gplay.core.domain.validation

interface FieldSpec {
    val rules: List<FieldRule>
}

inline val FieldSpec.key: String get() = this::class.qualifiedName.toString()

fun FieldSpec.makeFieldToValidate(value: String) = FieldToValidate(
    key = key,
    rules = rules,
    value = value,
)
