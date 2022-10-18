package com.dicoding.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String = "",
    val name: String = "",
    val avatarUrl: String = "",
    val location: String = "",
    val company: String = "",
    val publicRepos: Int = 0,
    val followers: Int = 0,
    val followersUrl: String = "",
    val following: Int = 0,
    val followingUrl: String = "",
): Parcelable
