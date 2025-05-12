package com.example.dynamic.favorite.di

import com.example.dynamic.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoriteViewModel(get()) }
}