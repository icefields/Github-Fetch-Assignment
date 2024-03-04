package org.scotia.githubfetcher.presentation.screens_detail.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.scotia.githubfetcher.presentation.PARAM_FORKS_TOTAL
import org.scotia.githubfetcher.presentation.PARAM_USER_NAME
import org.scotia.githubfetcher.presentation.PARAM_USER_URL
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    var state by mutableStateOf(UserScreenState())
        private set

    init {
        // TODO handle errors instead of just showing empty string
        state = UserScreenState(
            name = savedStateHandle.get<String>(PARAM_USER_NAME) ?: "",
            avatarUrl = savedStateHandle.get<String>(PARAM_USER_URL) ?: "",
            totalForks = savedStateHandle.get<Int>(PARAM_FORKS_TOTAL) ?: 0
        )
    }
}
