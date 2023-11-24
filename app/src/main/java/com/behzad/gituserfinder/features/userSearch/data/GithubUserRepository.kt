package com.behzad.gituserfinder.features.userSearch.data

class GithubUserRepository(private val gitHubApi: GitHubApi) {

    suspend fun searchUsers(query: String): List<GithubUser> {
        return gitHubApi.searchUsers(query).items
    }
}