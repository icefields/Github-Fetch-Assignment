package org.scotia.githubfetcher.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants
import org.scotia.githubfetcher.common.fontDimensionResource
import org.scotia.githubfetcher.domain.mockGithubUser
import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.presentation.common.TitleText

@Composable fun UserDetailSection(
    user: GithubUser,
    modifier: Modifier = Modifier,
    onUserClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onUserClick()
        }.testTag(Constants.TAG_USER_SECTION),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                // all I see from the video is that the image takes about 30/40% of the screen width
                .fillMaxWidth(0.4f)
                .padding(dimensionResource(id = R.dimen.userImg_padding)),
            model = user.avatarUrl,
            error = painterResource(id = R.drawable.ic_github),
            placeholder = painterResource(id = R.drawable.ic_github),
            contentScale = ContentScale.FillWidth,
            contentDescription = "github avatar image"
        )

        TitleText(text = user.name)
    }
}

@Preview(heightDp = 700, widthDp = 500)
@Composable
fun UserDetailSectionPreview() {
    UserDetailSection(
        user = mockGithubUser("icefields")
    ) { }
}
