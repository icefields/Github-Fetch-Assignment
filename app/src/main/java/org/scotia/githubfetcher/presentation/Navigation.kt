package org.scotia.githubfetcher.presentation

import androidx.navigation.NavController
import java.lang.StringBuilder
import java.net.URLEncoder

const val PARAM_REPO_NAME = "repoName"
const val PARAM_USER_ID = "userId"
const val PARAM_USER_NAME = "userName"
const val PARAM_USER_URL = "userAvatarUrl"
const val PARAM_FORKS_TOTAL = "totForks"

/**
 * represents all the screen we can navigate to and from
 * route: string representing the screen name
 * params: list of parameters to pass to the next screen. Params are by default strings, if a
 *      different type is needed it must be specified in as argument of the NavHost composable item
 */
sealed class Screen(val route: String, val params: List<String> = listOf()) {

    data object MainScreen: Screen(
        route = "main_screen"
    )

    data object RepositoryDetailScreen: Screen(
        route = "repo_detail_screen",
        params = listOf(PARAM_USER_ID, PARAM_REPO_NAME)
    )

    data object UserDetailScreen: Screen(
        route = "user_detail_screen",
        params = listOf(PARAM_USER_NAME, PARAM_USER_URL, PARAM_FORKS_TOTAL)
    )

    /**
     * generates the full route path for a specific screen
     */
    fun fullRoute() = StringBuilder(route).run {
        params.forEach { param ->
            append("/{")
            append(param)
            append("}")
        }
        toString()
    }
}

/**
 * utility function to navigate to the user screen
 */
fun NavController.navigateToUser(
    name: String,
    avatarUrl: String,
    totalForks: Int
) = navigate(Screen.UserDetailScreen.route +
        "/$name/${URLEncoder.encode(avatarUrl, "UTF-8")}/$totalForks"
)

/**
 * utility function to navigate to the repository screen
 */
fun NavController.navigateToRepo(
    userId: String,
    repoName: String
) = navigate(Screen.RepositoryDetailScreen.route +
        "/$userId/$repoName"
)
