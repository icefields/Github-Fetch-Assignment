package org.scotia.githubfetcher.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants.TAG_INPUT_VIEW
import org.scotia.githubfetcher.common.Constants.TAG_LIST_ITEM
import org.scotia.githubfetcher.common.Constants.TAG_LOADING_VIEW
import org.scotia.githubfetcher.common.Constants.TAG_REPO_LIST
import org.scotia.githubfetcher.common.Constants.TAG_USER_SECTION
import org.scotia.githubfetcher.domain.mockGithubUser
import org.scotia.githubfetcher.domain.mockRepos
import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.domain.models.Repository
import org.scotia.githubfetcher.presentation.common.GithubFetcherTopAppBar
import org.scotia.githubfetcher.presentation.navigateToRepo
import org.scotia.githubfetcher.presentation.navigateToUser
import org.scotia.githubfetcher.presentation.screens.components.RepositoryListItem
import org.scotia.githubfetcher.presentation.screens.components.UserDetailSection

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    MainScreenScaffold(
        user = mainViewModel.state.user,
        repos = mainViewModel.state.repos,
        isRefreshing = mainViewModel.state.isRepoDataLoading,
        isUserLoading = mainViewModel.state.isUserLoading,
        onUserClick = {
            navController.navigateToUser(
                name =mainViewModel.state.user?.name ?: "",
                avatarUrl = mainViewModel.state.user?.avatarUrl ?: "",
                totalForks = mainViewModel.state.totalForks
            )
        },
        onRepositoryClick = { repoName ->
            mainViewModel.state.user?.id?.let { userId ->
                navController.navigateToRepo(userId, repoName,)
            }
        },
        onRefresh = {
            mainViewModel.handleEvent(MainEvent.OnFetchRepos)
        }
    ) { username ->
        mainViewModel.handleEvent(MainEvent.OnFetchUser(username))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenScaffold(
    user: GithubUser?,
    repos: List<Repository>,
    isUserLoading: Boolean,
    isRefreshing: Boolean,
    onRepositoryClick: (repoName: String) -> Unit,
    onUserClick: () -> Unit,
    onRefresh: () -> Unit,
    onSubmitUsername: (username: String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GithubFetcherTopAppBar(
                name = stringResource(id = R.string.app_name),
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
        ) {
            MainScreenContent(
                user = user,
                repos = repos,
                isRefreshing = isRefreshing,
                isUserLoading = isUserLoading,
                onRepositoryClick = onRepositoryClick,
                onUserClick = onUserClick,
                onRefresh = onRefresh,
                onSubmitUsername = onSubmitUsername
            )
        }
    }
}

@Composable
fun MainScreenContent(
    user: GithubUser?,
    repos: List<Repository>,
    isUserLoading: Boolean,
    isRefreshing: Boolean,
    onRepositoryClick: (repoName: String) -> Unit,
    onUserClick: () -> Unit,
    onRefresh: () -> Unit,
    onSubmitUsername: (username: String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(
            horizontal = dimensionResource(id = R.dimen.screen_padding_horizontal),
            vertical = dimensionResource(id = R.dimen.screen_padding_vertical)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f).testTag(TAG_INPUT_VIEW),
                label = {
                    Text(text = stringResource(id = R.string.text_field_hint))
                },
                value = username,
                onValueChange = { username = it }
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.searchBtn_spacing_horizontal)))
            Button(
                onClick = {
                    onSubmitUsername(username)
                }
            ) {
                Text(text = stringResource(id = R.string.search_label))
            }
        }

        // animation required by instructions, from the video I see a fade in. There seems to be a
        // slide up vertically too, but hard to say for sure because of the slow fade in
        val userSectionVisible = user != null
        AnimatedVisibility(visible = isUserLoading && !userSectionVisible) {
            CircularProgressIndicator(modifier = Modifier.testTag(TAG_LOADING_VIEW)
                .fillMaxWidth()
                .height(30.dp)
            )
        }
        AnimatedVisibility(
            visible = userSectionVisible,
            enter = fadeIn(spring(stiffness = Spring.StiffnessVeryLow))
        ) {
            user?.let {
                UserDetailSection(
                    user = it,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onUserClick = onUserClick
                )
            }
        }

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onRefresh
        ) {
            // animation required by instructions, from the video I see the list slides up
            AnimatedVisibility(
                visible = userSectionVisible,
                enter = slideInVertically(spring(stiffness = Spring.StiffnessLow))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .testTag(TAG_REPO_LIST)
                ) {
                    items(repos) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.repoCard_spacing_vertical)))
                        RepositoryListItem(
                            modifier = Modifier.fillMaxWidth(),
                            repository = it) { repoName ->
                            onRepositoryClick(repoName)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        user = mockGithubUser("icefields"),
        repos = mockRepos(),
        onRepositoryClick = { },
        isRefreshing = false,
        isUserLoading = true,
        onRefresh = {},
        onUserClick = { }
    ) { }
}
