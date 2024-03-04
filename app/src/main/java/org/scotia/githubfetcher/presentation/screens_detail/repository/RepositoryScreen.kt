package org.scotia.githubfetcher.presentation.screens_detail.repository

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants
import org.scotia.githubfetcher.domain.mockRepository
import org.scotia.githubfetcher.domain.models.Repository
import org.scotia.githubfetcher.presentation.common.GithubFetcherTopAppBar
import org.scotia.githubfetcher.presentation.common.LoadingView
import org.scotia.githubfetcher.presentation.common.TextWithSubtitle

/**
 * As described in the official android documentation, we should not pass complex objects between
 * screens, we should rely on the app-domain data repositories instead.
 * In this screen's case, I'm passing the repo name and user name as navigation args, then used the
 * repository to retrieve the repository object.
 * Ideal we should create a Use-Case (as define in Clean-Architecture MVVM pattern) to get the repo
 * object, that we can just call from everywhere.
 *
 * https://developer.android.com/guide/navigation/use-graph/pass-data#supported_argument_types
 * Caution: Passing complex data structures over arguments is considered an anti-pattern.
 * Each destination should be responsible for loading UI data based on the minimum necessary
 * information, such as item IDs. This simplifies process recreation and avoids potential data
 * inconsistencies.
 *
 */
@Composable
fun RepositoryScreen(
    navController: NavController,
    viewModel: RepositoryViewModel = hiltViewModel()
) {
    if (viewModel.state.isNetworkLoading) {
        LoadingView()
    } else {
        RepositoryScreenContent(
            repository = viewModel.state.repository
        ) {
            navController.popBackStack()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreenContent(
    repository: Repository,
    onBackPressed: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GithubFetcherTopAppBar(
                name = repository.name,
                scrollBehavior = scrollBehavior,
                onBackClick = onBackPressed
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_padding_horizontal))
            ) {
                TextWithSubtitle(title = R.string.repositoryName_label,
                    subtitle = repository.name)
                TextWithSubtitle(title = R.string.repositoryDescription_label,
                    subtitle = repository.description)
                TextWithSubtitle(title = R.string.repositoryUpdate_label,
                    subtitle = repository.updatedAtDateFormatted())
                TextWithSubtitle(title = R.string.repositoryStars_label,
                    subtitle = repository.stargazersCount.toString())
                TextWithSubtitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Constants.TAG_FORKS),
                    title = R.string.forks_label,
                    subtitle = repository.forks.toString())
           }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryScreenPreview() {
    RepositoryScreenContent(repository = mockRepository()) { }
}
