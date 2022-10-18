package com.dicoding.githubuser.core.data.source.remote.network

import com.dicoding.githubuser.core.data.source.remote.response.SearchResponse
import com.dicoding.githubuser.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") apiKey: String
    ): List<UserResponse>

    @GET("users/{login}")
    suspend fun getUser(
        @Header("Authorization") apiKey: String,
        @Path("login") login: String
    ): UserResponse

    @GET("search/users")
    suspend fun getSearchedUsers(
        @Header("Authorization") apiKey: String,
        @Query("q") login: String
    ): SearchResponse
}