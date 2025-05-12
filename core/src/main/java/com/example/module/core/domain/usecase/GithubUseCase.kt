package com.example.module.core.domain.usecase

import com.example.module.core.data.Resource
import com.example.module.core.domain.model.DetailUser
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.model.UserGithub
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {

    fun getFavoriteUsers(): Flow<List<SimpleUsers>>
    fun getGithubUser(): Flow<Resource<List<UserGithub>>>
    fun getDetailUser(username: String): Flow<Resource<DetailUser>>
    fun searchUser(query: String): Flow<Resource<List<SimpleUsers>>>
    fun getUserFollowers(id: String): Flow<Resource<List<SimpleUsers>>>
    fun isFavoriteUser(id: String): Flow<Boolean>
    fun setFavoriteUser(user: DetailUser, state: Boolean)
}