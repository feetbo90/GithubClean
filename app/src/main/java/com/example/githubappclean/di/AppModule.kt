package com.example.githubappclean.di

import com.example.module.core.domain.usecase.GithubInteractor
import com.example.module.core.domain.usecase.GithubUseCase
import com.example.githubappclean.detail.DetailViewModel
import com.example.githubappclean.favorite.FavoriteViewModel
import com.example.githubappclean.follows.FollowsViewModel
import com.example.githubappclean.main.MainViewModel
import com.example.githubappclean.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GithubUseCase> { GithubInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FollowsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}