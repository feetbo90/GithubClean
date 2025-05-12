package com.example.module.core.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.module.core.R

class UserImageLoader {
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_person)
            .into(imageView)
    }
}
