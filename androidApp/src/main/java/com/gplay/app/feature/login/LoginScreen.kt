package com.gplay.app.feature.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.gplay.android.navigation.ScreenNavGraph
import com.gplay.app.main.MainUiEvent

data class LoginScreen(val onSendMainUiEvent: (MainUiEvent) -> Unit) : ScreenNavGraph {
    override val route: String = routeName
    override fun content(): @Composable (NavBackStackEntry) -> Unit = {
        LoginView(
            onSendMainUiEvent = onSendMainUiEvent,
        )
    }

    companion object {
        const val routeName = "login"
    }
}
