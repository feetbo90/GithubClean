package com.example.module.core.data.source.remote.network

import com.example.module.core.BuildConfig
import com.example.module.core.data.source.remote.response.NewGithubResponse
import com.example.module.core.data.source.remote.response.ResponseSearch
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.data.source.remote.response.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users?q=repos:>1")
    @Headers("Authorization: Bearer $API_TOKEN")
    suspend fun getGithubUsers(): NewGithubResponse

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getUserList(@Query("q") q: String): ResponseSearch

    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN")
    suspend fun getDetailUser(@Path("username") username: String): User

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getFollowersList(@Path("username") username: String): ArrayList<SimpleUser>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getFollowingList(@Path("username") username: String): ArrayList<SimpleUser>

    companion object {
        private const val API_TOKEN = BuildConfig.MY_API_TOKEN
    }
}