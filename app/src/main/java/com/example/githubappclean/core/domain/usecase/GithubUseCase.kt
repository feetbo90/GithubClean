package com.example.githubappclean.core.domain.usecase

import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.data.source.remote.response.User
import com.example.githubappclean.core.domain.model.UserGithub
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {

    fun getFavoriteUsers(): Flow<List<SimpleUser>>

    fun getGithubUser(): Flow<Resource<List<UserGithub>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun searchUser(query: String): Flow<Resource<List<SimpleUser>>>
    fun getUserFollowers(id: String): Flow<Resource<List<SimpleUser>>>
    fun isFavoriteUser(id: String): Flow<Boolean>
    fun setFavoriteUser(user: User, state: Boolean)
}