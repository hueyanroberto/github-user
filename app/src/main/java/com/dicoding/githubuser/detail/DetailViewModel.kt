package com.dicoding.githubuser.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.core.domain.model.User
import com.dicoding.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val userUseCase: UserUseCase): ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getUser(login: String): LiveData<com.dicoding.githubuser.core.data.Resource<List<User>>> {
        return userUseCase.getUser(login).asLiveData()
    }

    fun insertFavoriteUser(user: User) {
        runBlocking {
            userUseCase.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: User) {
        runBlocking {
            userUseCase.deleteFavoriteUser(user)
        }
    }

    fun checkFavoriteUser(login: String) {
        runBlocking {
            _isFavorite.value = userUseCase.checkFavoriteUser(login)
        }
    }
}