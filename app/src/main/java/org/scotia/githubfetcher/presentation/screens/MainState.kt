package org.scotia.githubfetcher.presentation.screens

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.domain.models.Repository

@Parcelize
data class MainState(
    // (user != null) is used to trigger animated visibility in the main screen
    val user: GithubUser? = null,
    val repos: List<Repository> = listOf(),
    val isUserLoading: Boolean = false,
    val isRepoDataLoading: Boolean = false,
): Parcelable {
    // this variable cannot be set, it's read only and will always return the current sum of forks
    val totalForks: Int = repos.sumOf { it.forks }
}
