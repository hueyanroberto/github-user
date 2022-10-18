package com.dicoding.githubuser.core.utlis

import com.dicoding.githubuser.core.data.source.local.entity.UserEntity
import com.dicoding.githubuser.core.data.source.remote.response.UserResponse
import com.dicoding.githubuser.core.domain.model.User

object DataMapper {
    fun mapResponseToEntities(input: List<UserResponse>): List<UserEntity> =
        input.map {
            UserEntity(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                followerUrl = it.followersUrl,
                following = it.following,
                followingUrl = it.followingUrl,
                company = it.company.toString(),
                location = it.location.toString(),
                publicRepos = it.publicRepos
            )
        }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                login = it.login,
                name = it.name.toString(),
                avatarUrl = it.avatarUrl.toString(),
                followers = it.followers,
                followersUrl = it.followerUrl.toString(),
                following = it.following,
                followingUrl = it.followingUrl.toString(),
                company = it.company.toString(),
                location = it.location.toString(),
                publicRepos = it.publicRepos
            )
        }

    fun mapDomainToEntities(input: List<User>): List<UserEntity> =
        input.map {
            UserEntity(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                followerUrl = it.followersUrl,
                following = it.following,
                followingUrl = it.followingUrl,
                company = it.company,
                location = it.location,
                publicRepos = it.publicRepos
            )
        }

    fun mapResponseToDomain(input:List<UserResponse>): List<User> =
        input.map {
            User(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                followersUrl = it.followersUrl,
                following = it.following,
                followingUrl = it.followingUrl,
                company = it.company.toString(),
                location = it.location.toString(),
                publicRepos = it.publicRepos
            )
        }
}