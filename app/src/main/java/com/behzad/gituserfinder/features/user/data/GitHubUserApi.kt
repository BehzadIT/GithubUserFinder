package com.behzad.gituserfinder.features.user.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q", encoded = true) query: String,
    ): GithubListResponse<GithubUser>

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): GithubUserDetail
}

@Serializable
data class GithubListResponse<T>(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    @SerialName("items") val items: List<T>,
)

@Serializable
data class GithubUser(
    @SerialName("login") val login: String,
    @SerialName("id") val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("gravatar_id") val gravatarId: String,
    @SerialName("url") val url: String,
    @SerialName("html_url") val htmUrl: String,
    @SerialName("followers_url") val followersUrl: String,
    @SerialName("following_url") val followingUrl: String,
    @SerialName("gists_url") val gistsUrl: String,
    @SerialName("starred_url") val starredUrl: String,
    @SerialName("subscriptions_url") val subscriptionsUrl: String,
    @SerialName("organizations_url") val organizationsUrl: String,
    @SerialName("repos_url") val reposUrl: String,
    @SerialName("events_url") val eventUrl: String,
    @SerialName("received_events_url") val receivedEventsUrl: String,
    @SerialName("type") val type: String,
    @SerialName("site_admin") val siteAdmin: Boolean,
    @SerialName("score") val score: Float
)

@Serializable
data class GithubUserDetail(
    @SerialName("login") val login: String,
    @SerialName("id") val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("gravatar_id") val gravatarId: String,
    @SerialName("url") val url: String,
    @SerialName("html_url") val htmUrl: String,
    @SerialName("followers_url") val followersUrl: String,
    @SerialName("following_url") val followingUrl: String,
    @SerialName("gists_url") val gistsUrl: String,
    @SerialName("starred_url") val starredUrl: String,
    @SerialName("subscriptions_url") val subscriptionsUrl: String,
    @SerialName("organizations_url") val organizationsUrl: String,
    @SerialName("repos_url") val reposUrl: String,
    @SerialName("events_url") val eventUrl: String,
    @SerialName("received_events_url") val receivedEventsUrl: String,
    @SerialName("type") val type: String,
    @SerialName("site_admin") val siteAdmin: Boolean,
    @SerialName("name") val name : String?,
    @SerialName("company") val company : String?,
    @SerialName("blog") val blog : String?,
    @SerialName("location") val location : String?,
    @SerialName("email") val email : String?,
    @SerialName("hireable") val hireable : Boolean?,
    @SerialName("bio") val bio : String?,
    @SerialName("twitter_username") val twitterUsername : String?,
    @SerialName("public_repos") val publicRepos : Int,
    @SerialName("public_gists") val publicGists : Int,
    @SerialName("followers") val followers : Int,
    @SerialName("following") val following : Int,
    @SerialName("created_at") val createdAt : String?,
    @SerialName("updated_at") val updatedAt : String?
)