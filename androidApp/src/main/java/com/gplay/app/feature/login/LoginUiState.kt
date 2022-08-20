package com.gplay.app.feature.login

import com.gplay.core.domain.validation.ValidationStates
import com.gplay.core.domain.validation.key

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: Throwable? = null,
    val validations: ValidationStates = mapOf(
        LoginFormSpec.Username.key to null,
        LoginFormSpec.Password.key to null,
    ),
)
