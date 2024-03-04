package org.scotia.githubfetcher.presentation.common

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.scotia.githubfetcher.R

@Composable
fun TextWithSubtitle(
    modifier: Modifier = Modifier.fillMaxWidth(),
    @StringRes title: Int,
    subtitle: String,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TitleText(text = stringResource(id = title))
            SubtitleText(
                text = subtitle,
            )
        }
    }
}

@Composable
@Preview
fun TextWithSubtitlePreview() {
    TextWithSubtitle(
        title = R.string.avatar_content_description,
        subtitle = "R.string.app_name"
    )
}
