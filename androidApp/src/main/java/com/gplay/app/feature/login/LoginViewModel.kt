package com.gplay.app.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.common.entity.onFailure
import com.gplay.core.domain.common.entity.onSuccess
import com.gplay.core.domain.validation.FieldToValidate
import com.gplay.core.domain.validation.makeFieldToValidate
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val formValidationUseCase: FormValidationUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    private val uiEvent = MutableSharedFlow<LoginUiEvent>()

    init {
        viewModelScope.launch { uiEvent.collect(::handleEvent) }
    }

    fun sendEvent(event: LoginUiEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private suspend fun handleEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.TypeUsername -> onUsernameChanged(event.value)
            is LoginUiEvent.TypePassword -> onPasswordChanged(event.value)
            is LoginUiEvent.SignIn -> signIn()
            is LoginUiEvent.ClearError -> _uiState.update { it.copy(error = null) }
        }
    }

    private suspend fun onUsernameChanged(value: String) {
        if (uiState.value.username != value) {
            _uiState.update { it.copy(username = value) }
        }
        onValidate(toValidate = LoginFormSpec.Username.makeFieldToValidate(value))
    }

    private suspend fun onPasswordChanged(value: String) {
        if (uiState.value.password != value) {
            _uiState.update { it.copy(password = value) }
        }
        onValidate(toValidate = LoginFormSpec.Password.makeFieldToValidate(value))
    }

    private suspend fun onValidate(toValidate: FieldToValidate) {
        val param = FormValidationUseCase.Param(
            toValidate = toValidate,
            current = uiState.value.validations,
        )
        formValidationUseCase(param)
            .collect { result ->
                _uiState.update { it.copy(validations = result) }
            }
    }

    private suspend fun signIn() {
        _uiState.update { it.copy(isLoading = true) }
        val param = with(uiState.value) {
            SignInUseCase.Param(username, password)
        }
        signInUseCase(param)
            .onSuccess {
                _uiState.update { it.copy(isSignedIn = true, isLoading = false) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(error = error, isLoading = false) }
            }
    }
}
