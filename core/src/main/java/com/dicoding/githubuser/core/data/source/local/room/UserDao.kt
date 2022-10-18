package com.dicoding.githubuser.core.data.source.local.room

import androidx.room.*
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteEntity
import com.dicoding.githubuser.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE login = :login")
    fun getSearchedUser(login: String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserEntity>)

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUser(login: String): Flow<List<UserEntity>>

    @Update
    suspend fun updateUser(user: List<UserEntity>): Int

    @Query("SELECT u.* FROM users u INNER JOIN favorites f ON u.login = f.login")
    fun getFavoriteUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteUser(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorites WHERE login = :login")
    suspend fun checkFavoriteUser(login: String): FavoriteEntity?
}