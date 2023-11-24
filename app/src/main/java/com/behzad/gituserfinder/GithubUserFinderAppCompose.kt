package com.behzad.gituserfinder

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.behzad.gituserfinder.features.userSearch.UserSearchScreen

@Composable
fun GithubUserFinderAppCompose() {
    val navController = rememberNavController()
    GithubUserFinderNavHost(
        navController = navController
    )
}

@Composable
fun GithubUserFinderNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            UserSearchScreen(navController= navController)
        }
    }
}