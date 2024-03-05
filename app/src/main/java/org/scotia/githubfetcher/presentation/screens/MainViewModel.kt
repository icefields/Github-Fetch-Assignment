package org.scotia.githubfetcher.presentation.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.scotia.githubfetcher.BuildConfig
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.domain.use_case.GetRepos
import org.scotia.githubfetcher.domain.use_case.GetUser
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUser,
    private val getReposUseCase: GetRepos,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by savedStateHandle.saveable { mutableStateOf(MainState()) }
        private set

    init {
        // the instructions didn't say anything about state restoration
        // disable in gradle (RESTORE_STATE = false) if not necessary
        if (BuildConfig.RESTORE_STATE) {
            state.user?.name?.let { username ->
                fetchData(username)
            }
        }
    }

    /**
     * this function handles all the UI events in one place
     */
    fun handleEvent(event: MainEvent) {
        when(event) {
            MainEvent.OnFetchRepos ->
                getRepos()
            is MainEvent.OnFetchUser ->
                fetchData(event.username)
        }
    }

    private fun fetchData(username: String) = viewModelScope.launch {
        // the 2 calls don't depend on each other's execution, they can be executed
        // asynchronously for better performance
        val userDataDeferred = async { getUserInfo(username) }
        val repoDataDeferred = async { getRepos(username) }
        userDataDeferred.await()
        repoDataDeferred.await()
    }

    private suspend fun getUserInfo(username: String) =
        getUserUseCase(username).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { user ->
                        state = state.copy(user = user)
                    }
                }
                is Resource.Error -> // TODO handle errors
                    state = state.copy(isUserLoading = false)
                is Resource.Loading ->
                    state = state.copy(isUserLoading = result.isLoading)
        }
    }.launchIn(viewModelScope)


    private suspend fun getRepos(username: String) =
        getReposUseCase(username).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { repos ->
                        state = state.copy(repos = repos)
                    }
                }
                is Resource.Error ->
                    state = state.copy(isRepoDataLoading = false)
                is Resource.Loading ->
                    state = state.copy(isRepoDataLoading = result.isLoading)

            }
        }.launchIn(viewModelScope)

    private fun getRepos() = viewModelScope.launch {
        state.user?.id?.let {
            getRepos(it)
        } ?: run {
            // TODO handle error
        }
    }
}
