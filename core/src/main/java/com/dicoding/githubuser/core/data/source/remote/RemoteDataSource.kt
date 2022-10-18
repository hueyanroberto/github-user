package com.dicoding.githubuser.core.data.source.remote

import android.util.Log
import com.dicoding.githubuser.core.BuildConfig
import com.dicoding.githubuser.core.data.source.remote.network.ApiResponse
import com.dicoding.githubuser.core.data.source.remote.network.ApiService
import com.dicoding.githubuser.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService){

    suspend fun getAllUsers(): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val users = apiService.getAllUsers(BuildConfig.API_KEY)
                if (users.isNotEmpty()) {
                    emit(ApiResponse.Success(users))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchedUsers(login: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchedUsers(BuildConfig.API_KEY, login)
                val users = response.users
                if (users.isNotEmpty()) {
                    emit(ApiResponse.Success(users))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUser(login: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val user = apiService.getUser(BuildConfig.API_KEY, login)
                val listUser = arrayListOf<UserResponse>()
                listUser.add(user)
                if (listUser.isNotEmpty()) {
                    emit(ApiResponse.Success(listUser))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}