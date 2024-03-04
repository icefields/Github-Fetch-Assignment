package org.scotia.githubfetcher.presentation.screens

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.scotia.githubfetcher.MainActivity
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants.TAG_INPUT_VIEW
import org.scotia.githubfetcher.common.Constants.TAG_LOADING_VIEW
import org.scotia.githubfetcher.common.Constants.TAG_REPO_LIST
import org.scotia.githubfetcher.common.Constants.TAG_USER_SECTION
import org.scotia.githubfetcher.data.REAL_USER_ID
import org.scotia.githubfetcher.di.AppModule
import org.scotia.githubfetcher.presentation.Screen
import org.scotia.githubfetcher.presentation.ui.theme.GithubFetcherAssignmentTheme

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainScreenTest {
    // rule for injecting dependencies
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // rule for interacting with composables, define activity to use
    @get:Rule(order = 1) // order: make sure we inject first then we call this rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            // make tests as isolated as possible, only test against the main screen here
            // we still want to put it in a nav controller
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
                }
            }
        }
    }

    @Test
    fun userSection_checkUserVisibility() {
        // we can test actions, ie. press button or we can make assertions, like check if a certain
        // text field contains a certain text, or make sure some button is enabled and so on

        // when screen starts make sure user is not visible, then when retrieved it will be visible
        composeRule.onNodeWithTag(TAG_USER_SECTION).assertDoesNotExist()
        inputUserAndSend()
        composeRule.onNodeWithTag(TAG_USER_SECTION).assertIsDisplayed()
    }

    @Test
    fun userSection_checkUserError() {
        composeRule.onNodeWithTag(TAG_USER_SECTION).assertDoesNotExist()
        inputUserAndSend("non-existent user")
        composeRule.onNodeWithTag(TAG_USER_SECTION).assertDoesNotExist()
    }

    @Test
    fun userSection_checkRepoListVisibility() {
        composeRule.onNodeWithTag(TAG_REPO_LIST).assertDoesNotExist()
        inputUserAndSend()
        composeRule.onNodeWithTag(TAG_REPO_LIST).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun progressIndicator_checkVisibilityAfterLoading() {
        inputUserAndSend()
//        composeRule.waitUntilExactlyOneExists(hasTestTag(TAG_LOADING_VIEW), 5000)

//        composeRule.waitUntil {
//            composeRule
//                .onAllNodesWithTag(TAG_LOADING_VIEW)
//                .fetchSemanticsNodes().isEmpty()
//        }
        // Verify UI state
        composeRule.onNodeWithTag(TAG_LOADING_VIEW).assertDoesNotExist()
    }

    private fun inputUserAndSend(userId: String = REAL_USER_ID) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TAG_INPUT_VIEW).performTextInput(userId)
        composeRule.onNodeWithText(context.getString(R.string.search_label)).performClick()
    }
}