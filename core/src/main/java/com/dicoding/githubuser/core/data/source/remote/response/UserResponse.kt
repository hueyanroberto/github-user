package com.dicoding.githubuser.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("followers")
    val followers: Int = 0,

    @field:SerializedName("avatar_url")
    val avatarUrl: String = "",

    @field:SerializedName("following_url")
    val followingUrl: String = "",

    @field:SerializedName("following")
    val following: Int = 0,

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("company")
    val company: String? = "",

    @field:SerializedName("location")
    val location: String? = "",

    @field:SerializedName("public_repos")
    val publicRepos: Int = 0,

    @field:SerializedName("login")
    val login: String = "",

    @field:SerializedName("followers_url")
    val followersUrl: String = ""
) : Parcelable

data class SearchResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val users: List<UserResponse>
)
