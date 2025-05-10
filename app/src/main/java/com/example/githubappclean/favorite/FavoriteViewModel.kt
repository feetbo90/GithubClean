package com.example.githubappclean.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.domain.usecase.GithubUseCase

class FavoriteViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getFavoriteUsers(): LiveData<List<SimpleUser>> = githubUseCase.getFavoriteUsers().asLiveData()
}