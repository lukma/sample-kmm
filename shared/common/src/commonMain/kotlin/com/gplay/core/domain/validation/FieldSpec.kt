package com.gplay.core.domain.validation

interface FieldSpec {
    val rules: List<FieldRule>
}

typealias FieldToValidate = Pair<FieldSpec, String>
