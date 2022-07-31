package com.gplay.app.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.gplay.android.navigation.ScreenNavGraph

object HomeScreen : ScreenNavGraph {
    override val route: String = "home"
    override fun content(): @Composable (NavBackStackEntry) -> Unit = { HomeView() }
}
