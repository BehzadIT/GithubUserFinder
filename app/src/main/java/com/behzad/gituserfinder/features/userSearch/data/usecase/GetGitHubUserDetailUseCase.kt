package com.behzad.gituserfinder.features.userSearch.data.usecase

import com.behzad.gituserfinder.features.userSearch.data.GithubUserDetail
import com.behzad.gituserfinder.features.userSearch.data.GithubUserRepository

class GetGitHubUserDetailUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(username: String): GithubUserDetail {
        return githubUserRepository.getUserDetail(username)
    }
}