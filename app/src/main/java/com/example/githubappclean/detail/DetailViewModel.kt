package com.example.githubappclean.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.module.core.data.Resource
import com.example.module.core.data.source.remote.response.User
import com.example.module.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {

    fun getDetails(username: String): LiveData<Resource<User>> {
        return githubUseCase.getDetailUser(username).asLiveData()
    }

    fun setFavoriteUser(user: User, newStatus:Boolean) =
        githubUseCase.setFavoriteUser(user, newStatus)

    fun isFavoriteGithub(id: String): Flow<Boolean> = githubUseCase.isFavoriteUser(id)
}