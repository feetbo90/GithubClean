package com.example.githubappclean.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.module.core.domain.usecase.GithubUseCase

class MainViewModel(githubUseCase: GithubUseCase) : ViewModel() {
    val githubUser = githubUseCase.getGithubUser().asLiveData()
}