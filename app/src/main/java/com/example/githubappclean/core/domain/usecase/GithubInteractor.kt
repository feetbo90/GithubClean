package com.example.githubappclean.core.domain.usecase

import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.data.source.remote.response.User
import com.example.githubappclean.core.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val githubRepository: IGithubRepository): GithubUseCase {

    override fun getGithubUser() = githubRepository.getGithubUser()

    override fun searchUser(query: String): Flow<Resource<List<SimpleUser>>> =
        githubRepository.searchUser(query)

    override fun getDetailUser(username: String): Flow<Resource<User>> =
        githubRepository.getDetailUser(username)

    override fun getUserFollowers(id: String): Flow<Resource<List<SimpleUser>>> =
        githubRepository.getUserFollowers(id)

    override fun isFavoriteUser(id: String): Flow<Boolean> = githubRepository.isFavoriteUser(id)

    override fun getFavoriteUsers(): Flow<List<SimpleUser>> =
        githubRepository.getFavoriteUsers()


    override fun setFavoriteUser(
        user: User,
        state: Boolean
    ) = githubRepository.setFavoriteUser(user, state)

}