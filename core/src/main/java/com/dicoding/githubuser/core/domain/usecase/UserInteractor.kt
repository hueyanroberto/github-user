package com.dicoding.githubuser.core.domain.usecase

import com.dicoding.githubuser.core.data.Resource
import com.dicoding.githubuser.core.domain.model.User
import com.dicoding.githubuser.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun getAllUsers(): Flow<Resource<List<User>>> = userRepository.getAllUsers()

    override fun getSearchedUser(login: String): Flow<Resource<List<User>>> = userRepository.getSearchedUser(login)

    override fun getUser(login: String): Flow<Resource<List<User>>> = userRepository.getUser(login)

    override suspend fun insertFavoriteUser(user: User) = userRepository.insertFavoriteUser(user)
    override suspend fun deleteFavoriteUser(user: User) = userRepository.deleteFavoriteUser(user)
    override suspend fun checkFavoriteUser(login: String): Boolean = userRepository.checkFavoriteUser(login)
    override fun getFavoriteUser(): Flow<List<User>> = userRepository.getFavoriteUser()
}