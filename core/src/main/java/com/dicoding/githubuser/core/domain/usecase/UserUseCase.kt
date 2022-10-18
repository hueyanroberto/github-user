package com.dicoding.githubuser.core.domain.usecase

import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun getSearchedUser(login: String): Flow<Resource<List<User>>>
    fun getUser(login: String): Flow<Resource<List<User>>>

    suspend fun insertFavoriteUser(user: User)
    suspend fun deleteFavoriteUser(user: User)
    suspend fun checkFavoriteUser(login: String): Boolean
    fun getFavoriteUser(): Flow<List<User>>
}