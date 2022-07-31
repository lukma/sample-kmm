package com.gplay.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

interface ScreenNavGraph {
    val route: String
    fun arguments(): List<NamedNavArgument> = emptyList()
    fun deepLinks(): List<NavDeepLink> = emptyList()
    fun content(): @Composable (NavBackStackEntry) -> Unit
}

fun ScreenNavGraph.build(builder: NavGraphBuilder) {
    val navGraph: NavGraphBuilder.() -> Unit = {
        composable(
            route = this@build.route,
            arguments = this@build.arguments(),
            deepLinks = this@build.deepLinks(),
            content = this@build.content(),
        )
    }
    navGraph(builder)
}
