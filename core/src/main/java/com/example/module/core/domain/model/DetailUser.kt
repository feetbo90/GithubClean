package com.example.module.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    val id: Int,
    val bio: String?,
    val login: String,
    val blog: String?,
    val followers: Int,
    val avatarUrl: String,
    val htmlUrl: String,
    val following: Int,
    val name: String?,
    val company: String?,
    val location: String?,
    val publicRepos: Int,
    val isFavorite: Boolean?
): Parcelable
