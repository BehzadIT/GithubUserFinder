package com.behzad.gituserfinder.features.user.data

import com.behzad.gituserfinder.features.shared.tryWithCustomExceptions

class GithubUserRepository(private val gitHubApi: GitHubApi) {

    suspend fun searchUsers(query: String): List<GithubUser> {
        return tryWithCustomExceptions { gitHubApi.searchUsers(query).items }
    }

    suspend fun getUserDetail(username: String): GithubUserDetail {
        return tryWithCustomExceptions { gitHubApi.getUserDetail(username) }
    }
}




