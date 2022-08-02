package com.gplay.app.main

import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.feature.login.LoginScreen

data class MainUiState(
    val isSignedIn: Boolean = false,
) {

    val startDestination: String
        get() = if (isSignedIn) HomeScreen.route else LoginScreen.route
}
