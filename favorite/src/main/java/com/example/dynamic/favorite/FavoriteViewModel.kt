package com.example.dynamic.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.usecase.GithubUseCase

class FavoriteViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    fun getFavoriteUsers(): LiveData<List<SimpleUsers>> = githubUseCase.getFavoriteUsers().asLiveData()
}