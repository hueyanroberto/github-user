package com.dicoding.githubuser.core.data.source.local

import com.dicoding.githubuser.core.data.source.local.entity.FavoriteEntity
import com.dicoding.githubuser.core.data.source.local.entity.UserEntity
import com.dicoding.githubuser.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {

    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()
    fun getUser(login: String): Flow<List<UserEntity>> = userDao.getUser(login)
    suspend fun insertUser(userList: List<UserEntity>) = userDao.insertUser(userList)
    suspend fun updateUser(user: List<UserEntity>): Int = userDao.updateUser(user)

    fun getFavoriteUsers(): Flow<List<UserEntity>> = userDao.getFavoriteUsers()
    suspend fun checkFavoriteUSer(login: String): FavoriteEntity? = userDao.checkFavoriteUser(login)
    suspend fun insertFavoriteUser(favoriteEntity: FavoriteEntity) = userDao.insertFavoriteUser(favoriteEntity)
    suspend fun deleteFavoriteUser(favoriteEntity: FavoriteEntity) = userDao.deleteFavoriteUser(favoriteEntity)
}