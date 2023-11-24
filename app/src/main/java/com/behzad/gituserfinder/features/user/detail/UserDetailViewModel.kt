package com.behzad.gituserfinder.features.user.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.behzad.gituserfinder.features.shared.LoadableData
import com.behzad.gituserfinder.features.shared.setAsLoadableData
import com.behzad.gituserfinder.features.user.data.GithubUserDetail
import com.behzad.gituserfinder.features.user.detail.usecase.GetGitHubUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class UserDetailViewModel(
    app: Application,
    private val username: String,
    private val getGitHubUserDetailUseCase: GetGitHubUserDetailUseCase
) : AndroidViewModel(app) {
    private val _userDetail =
        MutableStateFlow<LoadableData<GithubUserDetail>>(LoadableData.NotLoaded)
    val userDetail =
        _userDetail.stateIn(viewModelScope, SharingStarted.Lazily, LoadableData.NotLoaded)

    init {
        viewModelScope.launch {
            _userDetail.setAsLoadableData(getGitHubUserDetailUseCase(username))
        }

    }
}