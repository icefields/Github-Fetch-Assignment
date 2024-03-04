package org.scotia.githubfetcher.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.scotia.githubfetcher.MainActivity
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants
import org.scotia.githubfetcher.common.Constants.TAG_FORKS
import org.scotia.githubfetcher.common.Constants.TAG_LIST_ITEM
import org.scotia.githubfetcher.data.REAL_USER_ID
import org.scotia.githubfetcher.di.AppModule
import org.scotia.githubfetcher.presentation.screens.MainScreen
import org.scotia.githubfetcher.presentation.screens_detail.repository.RepositoryScreen
import org.scotia.githubfetcher.presentation.screens_detail.user.UserDetailScreen
import org.scotia.githubfetcher.presentation.ui.theme.GithubFetcherAssignmentTheme

/**
 * simulate what a user would really do in the app
 */
@HiltAndroidTest
@UninstallModules(AppModule::class)
class EndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            GithubFetcherAssignmentTheme {
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

    private fun inputUserAndSend(userId: String = REAL_USER_ID) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(Constants.TAG_INPUT_VIEW).performTextInput(userId)
        composeRule.onNodeWithText(context.getString(R.string.search_label)).performClick()
    }

    @Test
    fun completeEndToEndTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        inputUserAndSend()
        composeRule.onNodeWithTag(Constants.TAG_USER_SECTION).performClick()

        // go to new screen, verify all user data is displayed
        composeRule.onNodeWithTag(TAG_FORKS)
            .assertExists()
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription(context.getString(R.string.back_content_description)).performClick()

        //back to main screen
        composeRule.onNodeWithTag(Constants.TAG_REPO_LIST).assertIsDisplayed()
        // tap on one a list item
        composeRule.onAllNodesWithTag(TAG_LIST_ITEM)[0].performClick()
        composeRule.onNodeWithTag(TAG_FORKS)
            .assertExists()
            .assertIsDisplayed()
        // TODO verify every single UI element like below
        composeRule.onNodeWithText(context.getString(R.string.repositoryName_label)).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(context.getString(R.string.back_content_description)).performClick()

        //back to main screen
        composeRule.onNodeWithTag(Constants.TAG_REPO_LIST).assertIsDisplayed()
    }
}
