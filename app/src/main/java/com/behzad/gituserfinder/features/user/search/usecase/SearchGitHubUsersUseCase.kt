package com.behzad.gituserfinder.features.user.search.usecase

import com.behzad.gituserfinder.features.user.data.GithubUser
import com.behzad.gituserfinder.features.user.data.GithubUserRepository

class SearchGitHubUsersUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(query: String): List<GithubUser> {
        return githubUserRepository.searchUsers(query)
    }
}