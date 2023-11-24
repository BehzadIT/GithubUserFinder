package com.behzad.gituserfinder.features.userSearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.behzad.gituserfinder.features.shared.LoadableData
import com.behzad.gituserfinder.features.userSearch.data.GithubUser
import com.behzad.gituserfinder.features.userSearch.data.usecase.SearchGitHubUsersUseCase
import com.behzad.gituserfinder.features.shared.setAsLoadableData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class UserSearchViewModel(
    app: Application,
    private val searchGitHubUsersUseCase: SearchGitHubUsersUseCase
) : AndroidViewModel(app) {
    private val _searchResults =
        MutableStateFlow<LoadableData<List<GithubUser>>>(LoadableData.NotLoaded)
    val searchResults =
        _searchResults.stateIn(viewModelScope, SharingStarted.Lazily, LoadableData.NotLoaded)
    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.stateIn(viewModelScope, SharingStarted.Lazily, "")
    fun searchForUsers(searchQuery: String) {
        _searchQuery.value = searchQuery
        viewModelScope.launch {
            _searchResults.setAsLoadableData(searchGitHubUsersUseCase(searchQuery))
        }
    }
}