package com.gplay.app.feature.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.gplay.android.navigation.ScreenNavGraph

object LoginScreen : ScreenNavGraph {
    override val route: String = "login"
    override fun content(): @Composable (NavBackStackEntry) -> Unit = { LoginView() }
}
