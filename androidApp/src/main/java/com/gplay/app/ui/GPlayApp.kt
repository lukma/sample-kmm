package com.gplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gplay.app.ui.theme.GPlayTheme

@Composable
fun GPlayApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayApp()
    }
}
