package com.dicoding.githubuser.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity (
    @PrimaryKey
    @field:ColumnInfo(name = "login")
    val login: String = "",

    @field:ColumnInfo(name = "name")
    var name: String = "",

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String = "",
)