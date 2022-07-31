package com.gplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gplay.android.navigation.build
import com.gplay.app.feature.home.HomeRoute
import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.ui.theme.GPlayTheme

@Composable
fun GPlayApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeRoute.route) {
        HomeScreen.build(this)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayApp()
    }
}
