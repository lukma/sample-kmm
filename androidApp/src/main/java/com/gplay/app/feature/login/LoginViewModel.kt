package com.gplay.app.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gplay.core.domain.auth.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    fun onUsernameChanged(username: String) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(username = username))
        }
    }

    fun onPasswordChanged(password: String) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(password = password))
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.tryEmit(uiState.value.copy(isLoading = true))
            val param = with(uiState.value) {
                SignInUseCase.Param(username, password)
            }
            signInUseCase(param)
                .onFailure {
                    val value = uiState.value.copy(error = it, isLoading = false)
                    _uiState.emit(value)
                }
                .onSuccess {
                    val value = uiState.value.copy(isSignedIn = true, isLoading = false)
                    _uiState.emit(value)
                }
        }
    }

    fun clearError() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(error = null))
        }
    }
}
