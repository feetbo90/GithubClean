package com.example.module.core.utils

import com.example.module.core.data.source.local.entity.SimpleUserEntity
import com.example.module.core.data.source.local.entity.UserGithubEntity
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.data.source.remote.response.User
import com.example.module.core.data.source.remote.response.UserGithubResponse
import com.example.module.core.domain.model.DetailUser
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.model.UserGithub
import kotlin.collections.List

object DataMapper {

    fun mapResponseToDomainUser(it: User) = DetailUser(
        id = it.id,
        bio = it.bio,
        login = it.login,
        blog = it.blog,
        followers = it.followers,
        avatarUrl = it.avatarUrl,
        htmlUrl = it.htmlUrl,
        following = it.following,
        name = it.name,
        company = it.company,
        location = it.location,
        publicRepos = it.publicRepos,
        isFavorite = it.isFavorite
    )

    fun mapEntitiesToDomainNewGithub(input: List<UserGithubEntity>): List<UserGithub> =
        input.map {
            UserGithub(
                gistsUrl = it.gistsUrl,
                reposUrl = it.reposUrl,
                userViewType = it.userViewType,
                followingUrl = it.followingUrl,
                starredUrl = it.starredUrl,
                login = it.login,
                followersUrl = it.followersUrl,
                type = it.type,
                url = it.url,
                subscriptionsUrl = it.subscriptionsUrl,
                score = it.score,
                receivedEventsUrl = it.receivedEventsUrl,
                avatarUrl = it.avatarUrl,
                eventsUrl = it.eventsUrl,
                htmlUrl = it.htmlUrl,
                siteAdmin = it.siteAdmin,
                id = it.id,
                gravatarId = it.gravatarId,
                nodeId = it.nodeId,
                organizationsUrl = it.organizationsUrl
            )
        }

    fun mapResponsesGithubUserToEntities(input: List<UserGithubResponse>): List<UserGithubEntity> {
        val githubList = ArrayList<UserGithubEntity>()
        input.map {
            val github = UserGithubEntity(
                gistsUrl = it.gistsUrl,
                reposUrl = it.reposUrl,
                userViewType = it.userViewType,
                followingUrl = it.followingUrl,
                starredUrl = it.starredUrl,
                login = it.login,
                followersUrl = it.followersUrl,
                type = it.type,
                url = it.url,
                subscriptionsUrl = it.subscriptionsUrl,
                score = it.score,
                receivedEventsUrl = it.receivedEventsUrl,
                avatarUrl = it.avatarUrl,
                eventsUrl = it.eventsUrl,
                htmlUrl = it.htmlUrl,
                siteAdmin = it.siteAdmin,
                id = it.id,
                gravatarId = it.gravatarId,
                nodeId = it.nodeId,
                organizationsUrl = it.organizationsUrl
            )
            githubList.add(github)
        }
        return githubList
    }


    fun mapEntitiesToDomainUser(input: List<SimpleUserEntity>): List<SimpleUsers> =
        input.map {
            SimpleUsers(
                login = it.login,
                avatarUrl = it.avatarUrl,
                id = it.id
            )
        }

    fun mapDomainToEntity(it: DetailUser, state: Boolean) = SimpleUserEntity(
        id = it.id,
        login = it.login,
        avatarUrl = it.avatarUrl,
        isFavorite = state
    )

    fun mapResponseSimpleToDomain(it: List<SimpleUser>): List<SimpleUsers> =
        it.map {
            SimpleUsers(
                id = it.id,
                login = it.login,
                avatarUrl = it.avatarUrl,
            )
        }
}