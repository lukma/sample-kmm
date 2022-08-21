package com.gplay.app.feature.login

import com.gplay.core.domain.validation.ValidationState

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: Throwable? = null,
    val validations: Map<String, ValidationState> = mapOf(
        LoginFormSpec.Username.key to ValidationState.None,
        LoginFormSpec.Password.key to ValidationState.None,
    ),
)
