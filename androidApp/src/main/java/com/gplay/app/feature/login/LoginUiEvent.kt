package com.gplay.app.feature.login

sealed class LoginUiEvent {
    data class TypeUsername(val value: String) : LoginUiEvent()
    data class TypePassword(val value: String) : LoginUiEvent()
    object SignIn : LoginUiEvent()
    object ClearError : LoginUiEvent()
}
