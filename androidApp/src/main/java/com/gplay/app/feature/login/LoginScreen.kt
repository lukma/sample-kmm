package com.gplay.app.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.gplay.android.navigation.ScreenNavGraph
import com.gplay.app.main.MainUiEvent
import org.koin.androidx.compose.getViewModel

data class LoginScreen(val onSendMainUiEvent: (MainUiEvent) -> Unit) : ScreenNavGraph {
    override val route: String = routeName
    override fun content(): @Composable (NavBackStackEntry) -> Unit = {
        val viewModel: LoginViewModel = getViewModel()
        val uiState by viewModel.uiState.collectAsState()
        LoginView(
            uiState = uiState,
            onSendEvent = viewModel::sendEvent,
            onSigned = { onSendMainUiEvent(MainUiEvent.CheckIsSignedIn) },
        )
    }

    companion object {
        const val routeName = "login"
    }
}
