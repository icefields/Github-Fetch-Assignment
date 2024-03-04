package org.scotia.githubfetcher.presentation.screens_detail.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants.BADGE_FORKS_MIN
import org.scotia.githubfetcher.common.Constants.TAG_FORKS
import org.scotia.githubfetcher.common.fontDimensionResource
import org.scotia.githubfetcher.presentation.common.GithubFetcherTopAppBar
import org.scotia.githubfetcher.presentation.common.TitleText

/**
 * As described in the official android documentation, we should not pass complex objects between
 * screens, we should rely on the app-domain data repositories instead.
 * In the case of this app, for simplicity's sake, since I only have to pass 3 variables: name,
 * image and number of forks, I'm going to pass all 3 of them using navigation args.
 * Ideal we should create a Use-Case (as define in Clean-Architecture MVVM patters) to get the user
 * object, that we can just call from everywhere.
 *
 * https://developer.android.com/guide/navigation/use-graph/pass-data#supported_argument_types
 * Caution: Passing complex data structures over arguments is considered an anti-pattern.
 * Each destination should be responsible for loading UI data based on the minimum necessary
 * information, such as item IDs. This simplifies process recreation and avoids potential data
 * inconsistencies.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserScreenViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GithubFetcherTopAppBar(
                name = viewModel.state.name,
                scrollBehavior = scrollBehavior
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(top = dimensionResource(id = R.dimen.userDetailScreen_top_padding))
        ) {
            UserDetailScreenContent(
                name = viewModel.state.name,
                avatarUrl = viewModel.state.avatarUrl,
                totalForks = viewModel.state.totalForks
            )
        }
    }
}

@Composable
fun UserDetailScreenContent(
    name: String,
    avatarUrl: String,
    totalForks: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier =  Modifier
                .fillMaxWidth(0.7f),
            model = avatarUrl,
            error = painterResource(id = R.drawable.ic_github),
            placeholder = painterResource(id = R.drawable.ic_github),
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(id = R.string.avatar_content_description)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.userDetailScreen_spacing)))
        TitleText(text = R.string.totForks_label)
        TitleText(
            modifier = Modifier.testTag(TAG_FORKS),
            text = "$totalForks",
            fontSize = fontDimensionResource(id = R.dimen.forks_userDetail_text_size),
            textColour = if (totalForks < BADGE_FORKS_MIN) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.tertiary
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailScreenPreview() {
    UserDetailScreenContent(
        name = "viewModel.state.name",
        avatarUrl = "viewModel.state.avatarUrl",
        totalForks = 5698
    )
}
