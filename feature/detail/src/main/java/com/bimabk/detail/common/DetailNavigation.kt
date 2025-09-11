package com.bimabk.detail.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bimabk.detail.screen.DetailScreen

@Composable
internal fun DetailNavigation(
    navController: NavHostController,
    userId: String,
    finish: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = DetailRoute(userId = userId)
    ) {
        composable<DetailRoute> {
            val back = remember { finish }
            DetailScreen(
                onBack = back
            )
        }
    }
}