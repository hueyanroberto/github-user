package com.dicoding.githubuser.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.githubuser.core.data.source.local.entity.FavoriteEntity
import com.dicoding.githubuser.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}