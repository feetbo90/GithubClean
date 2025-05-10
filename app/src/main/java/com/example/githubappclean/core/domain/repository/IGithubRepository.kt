package com.example.githubappclean.core.domain.repository

import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.data.source.remote.response.User
import com.example.githubappclean.core.domain.model.UserGithub
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    fun getFavoriteUsers(): Flow<List<SimpleUser>>
    fun setFavoriteUser(user: User, state: Boolean)

    fun getGithubUser(): Flow<Resource<List<UserGithub>>>
    fun getUserFollowers(id: String): Flow<Resource<List<SimpleUser>>>
    fun searchUser(query: String): Flow<Resource<List<SimpleUser>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun isFavoriteUser(id: String): Flow<Boolean>
}