package com.behzad.gituserfinder.features.userSearch.data.usecase

import com.behzad.gituserfinder.features.userSearch.data.GithubUser
import com.behzad.gituserfinder.features.userSearch.data.GithubUserRepository

class SearchGitHubUsersUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(query: String): List<GithubUser> {
        return githubUserRepository.searchUsers(query)
    }
}