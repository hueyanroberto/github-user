package com.dicoding.githubuser.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.core.domain.model.User
import com.dicoding.githubuser.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase): ViewModel() {
    val user = userUseCase.getAllUsers().asLiveData()

    fun getSearchedUser(login: String): LiveData<com.dicoding.githubuser.core.data.Resource<List<User>>> {
        return userUseCase.getSearchedUser(login).asLiveData()
    }
}