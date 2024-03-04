package org.scotia.githubfetcher.presentation.screens

sealed class MainEvent {
    data class OnFetchUser(val username: String): MainEvent()
    data object OnFetchRepos: MainEvent()
}
