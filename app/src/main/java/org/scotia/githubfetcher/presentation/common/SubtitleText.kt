package org.scotia.githubfetcher.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.fontDimensionResource

@Composable
fun SubtitleText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    TitleText(
        modifier = modifier,
        text = stringResource(id = text),
    )
}

@Composable
fun SubtitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Light,
        fontSize = fontDimensionResource(id = R.dimen.subtitle_text_size)
    )
}
