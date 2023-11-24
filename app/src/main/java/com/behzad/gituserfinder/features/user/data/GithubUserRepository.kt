package com.behzad.gituserfinder.features.user.data

class GithubUserRepository(private val gitHubApi: GitHubApi) {

    suspend fun searchUsers(query: String): List<GithubUser> {
        return gitHubApi.searchUsers(query).items
    }
    suspend fun getUserDetail(username: String): GithubUserDetail {
        return gitHubApi.getUserDetail(username)
    }

}