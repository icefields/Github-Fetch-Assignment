package org.scotia.githubfetcher.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val id: String,
    val name: String,
    val avatarUrl: String
): Parcelable
