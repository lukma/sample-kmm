package com.gplay.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val isSignedInUseCase: IsSignedInUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get() = _uiState

    private val uiEvent = MutableSharedFlow<MainUiEvent>()

    init {
        viewModelScope.launch { uiEvent.collect(::handleEvent) }
    }

    fun sendEvent(event: MainUiEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private suspend fun handleEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.CheckIsSignedIn -> checkIsSignedIn()
        }
    }

    private suspend fun checkIsSignedIn() {
        isSignedInUseCase()
            .catch { emit(false) }
            .collectLatest { isSignedIn ->
                _uiState.update { it.copy(isSignedIn = isSignedIn) }
            }
    }
}
