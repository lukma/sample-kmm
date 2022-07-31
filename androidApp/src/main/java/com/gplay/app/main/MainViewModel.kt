package com.gplay.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.feature.login.LoginScreen
import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val isSignedInUseCase: IsSignedInUseCase,
) : ViewModel() {

    private val _startDestination = MutableStateFlow(HomeScreen.route)
    val startDestination: StateFlow<String> get() = _startDestination

    fun getStartDestination() {
        viewModelScope.launch {
            isSignedInUseCase()
                .catch { emit(false) }
                .collectLatest {
                    val value = if (it) HomeScreen.route else LoginScreen.route
                    _startDestination.tryEmit(value)
                }
        }
    }
}
