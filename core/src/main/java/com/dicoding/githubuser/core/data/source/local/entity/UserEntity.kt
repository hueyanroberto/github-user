package com.dicoding.githubuser.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @field:ColumnInfo(name = "login")
    val login: String = "",

    @field:ColumnInfo(name = "name")
    var name: String? = "",

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = "",

    @field:ColumnInfo(name = "followers")
    var followers: Int = 0,

    @field:ColumnInfo(name = "followers_url")
    var followerUrl: String? = "",

    @field:ColumnInfo(name = "following")
    var following: Int = 0,

    @field:ColumnInfo(name = "following_url")
    var followingUrl: String? = "",

    @field:ColumnInfo(name = "company")
    var company: String? = "",

    @field:ColumnInfo(name = "location")
    var location: String? = "",

    @field:ColumnInfo(name = "public_repos")
    var publicRepos: Int = 0

): Parcelable