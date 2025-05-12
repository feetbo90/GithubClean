package com.example.module.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseSearch(

    @field:SerializedName("items")
    val items: ArrayList<SimpleUser>
)

data class User(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("bio")
    val bio: String?,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("blog")
    val blog: String?,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("is_favorite")
        val isFavorite: Boolean?
)

data class SimpleUser(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String
)


