package com.example.module.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GithubUser")
data class UserGithubEntity(
    @ColumnInfo("gists_url")
    val gistsUrl: String? = null,

    @ColumnInfo("repos_url")
    val reposUrl: String? = null,

    @ColumnInfo("user_view_type")
    val userViewType: String? = null,

    @ColumnInfo("following_url")
    val followingUrl: String? = null,

    @ColumnInfo("starred_url")
    val starredUrl: String? = null,

    @ColumnInfo("login")
    val login: String? = null,

    @ColumnInfo("followers_url")
    val followersUrl: String? = null,

    @ColumnInfo("type")
    val type: String? = null,

    @ColumnInfo("url")
    val url: String? = null,

    @ColumnInfo("subscriptions_url")
    val subscriptionsUrl: String? = null,

    @ColumnInfo("score")
    val score: Double? = null,

    @ColumnInfo("received_events_url")
    val receivedEventsUrl: String? = null,

    @ColumnInfo("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo("events_url")
    val eventsUrl: String? = null,

    @ColumnInfo("html_url")
    val htmlUrl: String? = null,

    @ColumnInfo("site_admin")
    val siteAdmin: Boolean? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("gravatar_id")
    val gravatarId: String? = null,

    @ColumnInfo("node_id")
    val nodeId: String? = null,

    @ColumnInfo("organizations_url")
    val organizationsUrl: String? = null
)