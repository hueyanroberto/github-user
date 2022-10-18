package com.dicoding.githubuser.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubuser.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase): ViewModel() {
    val favoriteUser = userUseCase.getFavoriteUser().asLiveData()
}