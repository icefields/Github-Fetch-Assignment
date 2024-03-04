package org.scotia.githubfetcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.scotia.githubfetcher.presentation.PARAM_FORKS_TOTAL
import org.scotia.githubfetcher.presentation.PARAM_REPO_NAME
import org.scotia.githubfetcher.presentation.PARAM_USER_ID
import org.scotia.githubfetcher.presentation.PARAM_USER_NAME
import org.scotia.githubfetcher.presentation.PARAM_USER_URL
import org.scotia.githubfetcher.presentation.Screen
import org.scotia.githubfetcher.presentation.screens.MainScreen
import org.scotia.githubfetcher.presentation.screens_detail.repository.RepositoryScreen
import org.scotia.githubfetcher.presentation.screens_detail.user.UserDetailScreen
import org.scotia.githubfetcher.presentation.ui.theme.GithubFetcherAssignmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubFetcherAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {
                        composable(
                            route = Screen.MainScreen.route
                        ) {
                            MainScreen(navController = navController)
                        }

                        composable(
                            route = Screen.RepositoryDetailScreen.fullRoute()
                        ) {
                            RepositoryScreen(navController = navController)
                        }

                        composable(
                            route = Screen.UserDetailScreen.fullRoute(),
                            arguments = listOf(
                                navArgument(PARAM_USER_NAME) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(PARAM_USER_URL) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(PARAM_FORKS_TOTAL) {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) {
                            UserDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
