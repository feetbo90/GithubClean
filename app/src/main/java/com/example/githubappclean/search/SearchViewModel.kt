package com.example.githubappclean.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.module.core.data.Resource
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.usecase.GithubUseCase

class SearchViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {

    fun searchUser(username: String): LiveData<Resource<List<SimpleUsers>>> {
        return githubUseCase.searchUser(username).asLiveData()
    }
}