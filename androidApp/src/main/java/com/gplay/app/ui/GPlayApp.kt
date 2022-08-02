package com.gplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gplay.android.navigation.build
import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.feature.login.LoginScreen
import com.gplay.app.main.MainUiEvent
import com.gplay.app.main.MainUiState
import com.gplay.app.ui.theme.GPlayTheme

@Composable
fun GPlayApp(
    uiState: MainUiState,
    onSendEvent: (MainUiEvent) -> Unit,
) {
    GPlayScaffold {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = uiState.startDestination) {
            HomeScreen.build(this)
            LoginScreen(onSendEvent).build(this)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayApp(
            uiState = MainUiState(),
            onSendEvent = { /* no-op */ },
        )
    }
}
