package com.gplay.app.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.gplay.android.navigation.ScreenNavGraph
import com.gplay.android.navigation.ScreenNavRoute

object HomeScreen : ScreenNavGraph {
    override val navRoute: ScreenNavRoute = HomeRoute
    override fun content(): @Composable (NavBackStackEntry) -> Unit = { HomeView() }
}

object HomeRoute : ScreenNavRoute {
    override val route: String = "home"
}
