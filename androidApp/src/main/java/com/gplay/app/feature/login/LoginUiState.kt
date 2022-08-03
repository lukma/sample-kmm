package com.gplay.app.feature.login

import com.gplay.core.domain.validation.ValidationStates

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: Throwable? = null,
    val validations: ValidationStates = mapOf(
        LoginFormSpec.Username to null,
        LoginFormSpec.Password to null,
    ),
)
