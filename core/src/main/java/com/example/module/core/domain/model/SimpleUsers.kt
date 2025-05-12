package com.example.module.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleUsers(
    var id: Int,
    val avatarUrl: String,
    val login: String
): Parcelable