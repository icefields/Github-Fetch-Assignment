package org.scotia.githubfetcher.presentation.screens_detail.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.domain.use_case.GetRepository
import org.scotia.githubfetcher.presentation.PARAM_REPO_NAME
import org.scotia.githubfetcher.presentation.PARAM_USER_ID
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(RepositoryState())
        private set

    init {
        savedStateHandle.get<String>(PARAM_REPO_NAME)?.let { repoName ->
            savedStateHandle.get<String>(PARAM_USER_ID)?.let { userId ->
                getRepoInfo(
                    username = userId,
                    repoName = repoName
                )
            }
        }
    }

    private fun getRepoInfo(
        username: String,
        repoName: String
    ) = viewModelScope.launch {
        getRepositoryUseCase(
            username = username,
            repoName = repoName
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { repository ->
                        state = state.copy(repository = repository)
                    }
                }
                is Resource.Error -> // TODO handle errors
                    state = state.copy(isNetworkLoading = false)
                is Resource.Loading ->
                    state = state.copy(isNetworkLoading = result.isLoading)
            }
        }.launchIn(viewModelScope)
    }
}
