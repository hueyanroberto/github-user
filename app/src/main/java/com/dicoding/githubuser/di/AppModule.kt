package com.dicoding.githubuser.di

import com.dicoding.githubuser.main.MainViewModel
import com.dicoding.githubuser.core.domain.usecase.UserInteractor
import com.dicoding.githubuser.core.domain.usecase.UserUseCase
import com.dicoding.githubuser.detail.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> {UserInteractor(get())}
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}