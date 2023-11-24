package com.behzad.gituserfinder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.behzad.gituserfinder.features.userDetail.UserDetailScreen
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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            UserSearchScreen(navController = navController)
        }
        composable(
            "userDetail?username={username}&avatar={avatar}", arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                },
                navArgument("avatar") {
                    type = NavType.StringType
                })
        ) {
            UserDetailScreen(
                navController = navController,
                username = requireNotNull(it.arguments?.getString("username")),
                avatar = requireNotNull(it.arguments?.getString("avatar"))
            )
        }

    }
}