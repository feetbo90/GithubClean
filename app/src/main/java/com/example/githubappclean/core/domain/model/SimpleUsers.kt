package com.example.githubappclean.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleUsers(
    var id: String,
    val avatarUrl: String,
    val login: String
): Parcelable