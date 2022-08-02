package com.gplay.app.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gplay.core.domain.auth.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    private val uiEvent = MutableSharedFlow<LoginUiEvent>()

    init {
        viewModelScope.launch {
            uiEvent.collect(::handleEvent)
        }
    }

    fun sendEvent(event: LoginUiEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private fun handleEvent(event: LoginUiEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginUiEvent.TypeUsername -> _uiState.update { it.copy(username = event.value) }
                is LoginUiEvent.TypePassword -> _uiState.update { it.copy(password = event.value) }
                is LoginUiEvent.SignIn -> signIn()
                is LoginUiEvent.ClearError -> _uiState.update { it.copy(error = null) }
            }
        }
    }

    private suspend fun signIn() {
        _uiState.update { it.copy(isLoading = true) }
        val param = with(uiState.value) {
            SignInUseCase.Param(username, password)
        }
        signInUseCase(param)
            .onFailure { error ->
                _uiState.update { it.copy(error = error, isLoading = false) }
            }
            .onSuccess {
                _uiState.update { it.copy(isSignedIn = true, isLoading = false) }
            }
    }
}
