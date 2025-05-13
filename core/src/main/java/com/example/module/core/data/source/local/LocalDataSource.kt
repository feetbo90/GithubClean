package com.example.module.core.data.source.local

import com.example.module.core.data.source.local.entity.SimpleUserEntity
import com.example.module.core.data.source.local.entity.UserGithubEntity
import com.example.module.core.data.source.local.room.GithubDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val githubDao: GithubDao){

    fun getFavoriteUsers(): Flow<List<SimpleUserEntity>> = githubDao.getFavoriteUsers()

    fun setFavoriteUser(user: SimpleUserEntity, newState: Boolean) {
        user.isFavorite = newState
        githubDao.updateFavoriteUser(user)
    }

    fun isFavoriteUser(id: String): Flow<Boolean> = githubDao.isFavoriteUser(id)

    fun getGithubUsers(): Flow<List<UserGithubEntity>> = githubDao.getGithubUsers()

    suspend fun insertGithubUser(userList: List<UserGithubEntity>) = githubDao.insertGithubUser(userList)

}