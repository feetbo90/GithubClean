package com.example.githubappclean.core.utils

import com.example.githubappclean.core.data.source.local.entity.SimpleUserEntity
import com.example.githubappclean.core.data.source.local.entity.UserGithubEntity
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.data.source.remote.response.User
import com.example.githubappclean.core.data.source.remote.response.UserGithubResponse
import com.example.githubappclean.core.domain.model.SimpleUsers
import com.example.githubappclean.core.domain.model.UserGithub

object DataMapper {
    fun mapEntitiesToDomain(input: List<SimpleUserEntity>): List<SimpleUsers> =
        input.map {
            SimpleUsers(
                id = it.id.toString(),
                avatarUrl = it.avatarUrl,
                login = it.login,
            )
        }

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


    fun mapEntitiesToDomainUser(input: List<SimpleUserEntity>): List<SimpleUser> =
        input.map {
            SimpleUser(
                login = it.login,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapDomainToEntity(it: User, state: Boolean) = SimpleUserEntity(
        id = it.id,
        login = it.login,
        avatarUrl = it.avatarUrl,
        isFavorite = state
    )
}