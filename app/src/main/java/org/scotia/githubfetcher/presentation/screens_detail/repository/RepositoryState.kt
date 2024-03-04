package org.scotia.githubfetcher.presentation.screens_detail.repository

import org.scotia.githubfetcher.domain.models.Repository

data class RepositoryState(
    val isNetworkLoading: Boolean = false,
    val repository: Repository = Repository.emptyRepository
)
