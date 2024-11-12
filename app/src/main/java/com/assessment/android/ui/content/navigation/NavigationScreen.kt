package com.assessment.android.ui.content.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assessment.android.ui.content.main.MainScreen
import com.assessment.android.ui.content.main.MainViewModel
import com.assessment.android.ui.content.search.SearchContentScreen

@Composable
fun NavigationView(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController, mainViewModel)
        }
        composable("search") {
            SearchContentScreen(
                onMain = { navController.popBackStack() }
            )
        }
    }
}