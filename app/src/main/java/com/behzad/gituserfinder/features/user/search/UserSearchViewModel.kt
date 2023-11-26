package com.behzad.gituserfinder.features.user.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.behzad.gituserfinder.features.shared.LoadableData
import com.behzad.gituserfinder.features.shared.setAsLoadableData
import com.behzad.gituserfinder.features.user.data.GithubUser
import com.behzad.gituserfinder.features.user.search.usecase.SearchGitHubUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class UserSearchViewModel(
    app: Application, private val searchGitHubUsersUseCase: SearchGitHubUsersUseCase
) : AndroidViewModel(app) {
    private val _searchResults =
        MutableStateFlow<LoadableData<List<GithubUser>>>(LoadableData.NotLoaded)
    val searchResults =
        _searchResults.stateIn(viewModelScope, SharingStarted.Lazily, LoadableData.NotLoaded)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.stateIn(viewModelScope, SharingStarted.Lazily, "")

    init {
        viewModelScope.launch {
            _searchQuery.filter { it.isValidSearchQuery() }.map { searchQuery ->
                _searchResults.value = LoadableData.Loading()
                searchQuery
            }.debounce(1000).collect {
                _searchResults.setAsLoadableData { searchGitHubUsersUseCase(it) }
            }
        }

    }

    fun searchForUsers(searchQuery: String) {
        _searchQuery.value = searchQuery
    }
}
internal fun String.isValidSearchQuery() = trimStart().length >= 2