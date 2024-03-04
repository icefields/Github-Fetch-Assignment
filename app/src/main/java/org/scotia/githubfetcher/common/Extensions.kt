package org.scotia.githubfetcher.common

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.scotia.githubfetcher.common.Constants.DATE_FORMAT_APP
import org.scotia.githubfetcher.common.Constants.DATE_FORMAT_GITHUB
import org.scotia.githubfetcher.common.Constants.ERROR_STRING
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val Int.dpTextUnit: TextUnit
    @Composable
    get() = with(LocalDensity.current) { this@dpTextUnit.dp.toSp() }

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

/**
 * 2022-02-25T19:05:00+00:00
 * YYYY-MM-DDThh:mm:ssTZD (eg
 * 1997-07-16T19:20:30+01:00)
 * val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssO")//("YYYY-MM-DD'T'hh:mm:ss'T'ZD")
 *
 * 2023-11-13T19:32:57Z
 */
fun String.isoZonedDateToLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT_GITHUB))

fun LocalDateTime.toFormattedString() =
    format(DateTimeFormatter.ofPattern(DATE_FORMAT_APP)) ?: ERROR_STRING

