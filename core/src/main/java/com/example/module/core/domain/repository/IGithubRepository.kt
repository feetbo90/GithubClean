package com.example.module.core.domain.repository

import com.example.module.core.data.Resource
import com.example.module.core.domain.model.DetailUser
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.model.UserGithub
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    fun getFavoriteUsers(): Flow<List<SimpleUsers>>
    fun setFavoriteUser(user: DetailUser, state: Boolean)

    fun getGithubUser(): Flow<Resource<List<UserGithub>>>
    fun getUserFollowers(id: String): Flow<Resource<List<SimpleUsers>>>
    fun searchUser(query: String): Flow<Resource<List<SimpleUsers>>>
    fun getDetailUser(username: String): Flow<Resource<DetailUser>>
    fun isFavoriteUser(id: String): Flow<Boolean>
}