package com.behzad.gituserfinder

import com.behzad.gituserfinder.features.shared.HttpClientException
import com.behzad.gituserfinder.features.shared.HttpServerException
import com.behzad.gituserfinder.features.shared.NoInternetConnectionException
import com.behzad.gituserfinder.features.user.data.GitHubApi
import com.behzad.gituserfinder.features.user.data.GithubUserRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

class GihubReposiotrySearchUserTest {
    private val sampleUserName: String = "this user does not exist"
    private val gitHubApi: GitHubApi = mock()
    private val anyNotNullable = ArgumentMatchers.any(String()::class.java) ?: String()
    private val githubUserRepository = GithubUserRepository(gitHubApi)

    @Test
    fun searchUsers_4xxError_expect_ThrowHttpClientError() {
        runBlocking {
            val exception = HttpException(Response.error<Any?>(404, "".toResponseBody()))
            whenever(gitHubApi.searchUsers(anyNotNullable)).thenThrow(exception)
            assertThrows<HttpClientException> {
                githubUserRepository.searchUsers(sampleUserName)
            }
            verify(gitHubApi).searchUsers(sampleUserName)
        }
    }

    @Test
    fun searchUsers_5xxError_expect_ThrowHttpServerError() {
        runBlocking {
            val exception = HttpException(Response.error<Any?>(500, "".toResponseBody()))
            whenever(gitHubApi.searchUsers(anyNotNullable)).thenThrow(exception)
            assertThrows<HttpServerException> {
                githubUserRepository.searchUsers(sampleUserName)
            }
            verify(gitHubApi).searchUsers(sampleUserName)
        }
    }

    @Test
    fun searchUsers_IoException_expect_ThrowNoConnectivityException() {
        runBlocking {
            whenever(gitHubApi.searchUsers(anyNotNullable)).thenAnswer { throw IOException() }
            assertThrows<NoInternetConnectionException> {
                githubUserRepository.searchUsers(sampleUserName)
            }
            verify(gitHubApi).searchUsers(sampleUserName)
        }
    }

    @Test
    fun searchUsers_OtherException_expect_RethrowSameException() {
        runBlocking {
            whenever(gitHubApi.searchUsers(anyNotNullable)).thenAnswer { throw Exception() }
            assertThrows<Exception> {
                githubUserRepository.searchUsers(sampleUserName)
            }
            verify(gitHubApi).searchUsers(sampleUserName)
        }
    }
}