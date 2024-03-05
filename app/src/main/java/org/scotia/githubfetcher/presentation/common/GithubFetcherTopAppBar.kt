package org.scotia.githubfetcher.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.scotia.githubfetcher.common.Constants.TAG_USER_DETAIL_NAME

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GithubFetcherTopAppBar(
    name: String,
    scrollBehavior: TopAppBarScrollBehavior,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(Color.Transparent),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        title = {
            Text(modifier = Modifier
                    .basicMarquee()
                    .padding(horizontal = 15.dp)
                    .testTag(TAG_USER_DETAIL_NAME),
                text = name,
                maxLines = 1,
                fontWeight = FontWeight.Normal
            )
        },
        navigationIcon = {
            if (showBackButton) {
                CircleBackButton(onBackClick = onBackClick)
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubFetcherTopAppBar(
    name: String,
    scrollBehavior: TopAppBarScrollBehavior
) {
    GithubFetcherTopAppBar(
        name = name,
        scrollBehavior = scrollBehavior,
        showBackButton = false
    ) { }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 300) //(widthDp = 50, heightDp = 50)
@Composable
fun GithubFetcherTopAppBarTopBarPreview() {
    GithubFetcherTopAppBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        onBackClick = {},
        name = "Icefields"
    )
}
