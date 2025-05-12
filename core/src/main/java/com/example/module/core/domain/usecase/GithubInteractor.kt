package com.example.module.core.domain.usecase

import com.example.module.core.data.Resource
import com.example.module.core.domain.model.DetailUser
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val githubRepository: IGithubRepository): GithubUseCase {

    override fun getGithubUser() = githubRepository.getGithubUser()

    override fun searchUser(query: String): Flow<Resource<List<SimpleUsers>>> =
        githubRepository.searchUser(query)

    override fun getDetailUser(username: String): Flow<Resource<DetailUser>> =
        githubRepository.getDetailUser(username)

    override fun getUserFollowers(id: String): Flow<Resource<List<SimpleUsers>>> =
        githubRepository.getUserFollowers(id)

    override fun isFavoriteUser(id: String): Flow<Boolean> = githubRepository.isFavoriteUser(id)

    override fun getFavoriteUsers(): Flow<List<SimpleUsers>> =
        githubRepository.getFavoriteUsers()


    override fun setFavoriteUser(
        user: DetailUser,
        state: Boolean
    ) = githubRepository.setFavoriteUser(user, state)

}