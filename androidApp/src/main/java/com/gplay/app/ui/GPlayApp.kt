package com.gplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gplay.android.navigation.build
import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.feature.login.LoginScreen
import com.gplay.app.ui.theme.GPlayTheme

@Composable
fun GPlayApp(
    startDestination: String,
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination) {
        HomeScreen.build(this)
        LoginScreen.build(this)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayApp(
            startDestination = HomeScreen.route,
        )
    }
}
