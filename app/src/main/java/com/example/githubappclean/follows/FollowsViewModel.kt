package com.example.githubappclean.follows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.domain.usecase.GithubUseCase

class FollowsViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getUserFollowers(username: String): LiveData<Resource<List<SimpleUser>>> {
        return githubUseCase.getUserFollowers(username).asLiveData()
    }
}