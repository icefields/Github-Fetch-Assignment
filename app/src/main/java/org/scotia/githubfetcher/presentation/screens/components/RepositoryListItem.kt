package org.scotia.githubfetcher.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.Constants
import org.scotia.githubfetcher.domain.mockRepository
import org.scotia.githubfetcher.domain.models.Repository
import org.scotia.githubfetcher.presentation.common.SubtitleText
import org.scotia.githubfetcher.presentation.common.TitleText

@Composable
fun RepositoryListItem(
    repository: Repository,
    modifier: Modifier = Modifier,
    onItemClick: (repoName: String) -> Unit
) {
    Card(
        //border = BorderStroke((0.5).dp, MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.repoCard_padding))
                .clickable {
                    onItemClick(repository.name)
                }.testTag(Constants.TAG_LIST_ITEM)

        ) {
            TitleText(
                modifier = Modifier.fillMaxWidth(),
                text = repository.name
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.repoCard_spacing_center)))
            SubtitleText(
                modifier = Modifier.fillMaxWidth(),
                text = repository.description
            )
        }
    }
}

@Preview
@Composable
fun RepositoryListItemPreview() {
    RepositoryListItem(
        repository = mockRepository("my repository title")
    ) { }
}
