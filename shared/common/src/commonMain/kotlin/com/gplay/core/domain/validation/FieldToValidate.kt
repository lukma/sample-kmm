package com.gplay.core.domain.validation

data class FieldToValidate(
    val key: String,
    val rules: List<FieldRule>,
    val value: String
)
