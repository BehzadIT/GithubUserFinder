package com.behzad.gituserfinder.features.user.detail.usecase

import com.behzad.gituserfinder.features.user.data.GithubUserDetail
import com.behzad.gituserfinder.features.user.data.GithubUserRepository

class GetGitHubUserDetailUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(username: String): GithubUserDetail {
        return githubUserRepository.getUserDetail(username)
    }
}