package org.scotia.githubfetcher.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import org.scotia.githubfetcher.R
import org.scotia.githubfetcher.common.fontDimensionResource

@Composable
fun TitleText(
    @StringRes text: Int,
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.title_text_size),
    modifier: Modifier = Modifier
) {
    TitleText(
        modifier = modifier,
        fontSize = fontSize,
        text = stringResource(id = text),
    )
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.title_text_size),
    textColour: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        color = textColour
    )
}
