package com.behzad.gituserfinder.features.user.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    /**
     * @throws java.io.IOException if the member is not of type {@code JsonPrimitive}.
     *
     */
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q", encoded = true) query: String,
    ): GithubListResponse<GithubUser>

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): GithubUserDetail
}

