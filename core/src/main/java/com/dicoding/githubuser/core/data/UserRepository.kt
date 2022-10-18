package com.dicoding.githubuser.core.data

import com.dicoding.githubuser.core.data.source.local.LocalDataSource
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteEntity
import com.dicoding.githubuser.core.data.source.remote.RemoteDataSource
import com.dicoding.githubuser.core.data.source.remote.network.ApiResponse
import com.dicoding.githubuser.core.data.source.remote.response.UserResponse
import com.dicoding.githubuser.core.domain.model.User
import com.dicoding.githubuser.core.domain.repository.IUserRepository
import com.dicoding.githubuser.core.utlis.AppExecutors
import com.dicoding.githubuser.core.utlis.DataMapper
import kotlinx.coroutines.flow.*

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IUserRepository{

    override fun getAllUsers(): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getAllUsers().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getAllUsers()
            }

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val userList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertUser(userList)
            }
        }.asFlow()

    override fun getSearchedUser(login: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getSearchedUsers(login).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf<User>()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }
    }

    override fun getUser(login: String): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getUser(login).map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return if (data == null) true
                else {
                    if (data.isEmpty()) return true
                    else data[0].name.isEmpty()
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getUser(login)
            }

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val userList = DataMapper.mapResponseToEntities(data)
                val updateCount = localDataSource.updateUser(userList)
                if (updateCount < 1) {
                    localDataSource.insertUser(userList)
                }
            }

        }.asFlow()

    override suspend fun insertFavoriteUser(user: User) {
        val favorite = FavoriteEntity(
            login = user.login,
            avatarUrl = user.avatarUrl,
            name = user.name
        )
        localDataSource.insertFavoriteUser(favorite)
    }

    override suspend fun deleteFavoriteUser(user: User) {
        val favorite = FavoriteEntity(
            login = user.login,
            avatarUrl = user.avatarUrl,
            name = user.name
        )
        localDataSource.deleteFavoriteUser(favorite)
    }

    override suspend fun checkFavoriteUser(login: String): Boolean {
        val favorite: FavoriteEntity? = localDataSource.checkFavoriteUSer(login)
        return favorite != null
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUsers().map { DataMapper.mapEntitiesToDomain(it) }
    }
}